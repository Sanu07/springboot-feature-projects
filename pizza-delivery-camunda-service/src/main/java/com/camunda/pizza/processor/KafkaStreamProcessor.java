package com.camunda.pizza.processor;

import com.camunda.pizza.entity.PizzaOrder;
import com.camunda.pizza.enums.PizzaOrderStatus;
import com.camunda.pizza.model.TopicMessage;
import com.camunda.pizza.producers.KafkaProducer;
import com.camunda.pizza.service.PizzaService;
import com.camunda.pizza.zeebe.ZeebeWorkflowInstanceStarter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

@Component
@Slf4j
public class KafkaStreamProcessor {

    @Autowired
    KafkaProducer kafkaProducer;

    @Autowired
    private PizzaService pizzaService;

    @Value("${pizza.delivery.workflow}")
    private String workflow;
    @Autowired
    private ZeebeWorkflowInstanceStarter zeebeWorkflowInstanceStarter;

    @Autowired
    private ObjectMapper mapper;
    @Bean
    public Consumer<TopicMessage> orderPizza() {
        return message -> {
            Map<String, Object> variables = new HashMap<>();
            variables.put("name", message.getName());
            variables.put("status", message.getStatus());
            variables.put("correlationKey", message.getCorrelationKey());

            String processInstanceKey = zeebeWorkflowInstanceStarter.startProcessInstance(variables, workflow);
            log.info("Started processInstance: {}", processInstanceKey);
        };
    }

    @Bean
    public Function<String, PizzaOrder> processPizzaOrderStart() {
        return receivedOrder -> {
            log.info("Received order :: -> {}", receivedOrder);
            JsonNode jsonNode = null;
            try {
                jsonNode = mapper.readTree(receivedOrder);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            TopicMessage receivedOrder2 = mapper.convertValue(jsonNode.get("variablesAsMap"), TopicMessage.class);
            PizzaOrder pizzaOrderCreated = PizzaOrder.builder()
                    .name(receivedOrder2.getName())
                    .status(PizzaOrderStatus.ORDER_CREATED)
                    .correlationKey(receivedOrder2.getCorrelationKey())
                    .createdAt(OffsetDateTime.now())
                    .modifiedAt(OffsetDateTime.now())
                    .build();
            PizzaOrder pizzaOrderCreatedDb = pizzaService.save(pizzaOrderCreated);
            log.info("Order created :: -> {}", pizzaOrderCreatedDb);
            PizzaOrder pizzaOrderDelivered = PizzaOrder.builder()
                    .name(receivedOrder2.getName())
                    .status(PizzaOrderStatus.ORDER_DELIVERED)
                    .correlationKey(receivedOrder2.getCorrelationKey())
                    .createdAt(OffsetDateTime.now())
                    .modifiedAt(OffsetDateTime.now())
                    .build();
            PizzaOrder pizzaOrderDeliveredDb = pizzaService.save(pizzaOrderDelivered);
            log.info("Order delivered :: -> {}", pizzaOrderDelivered);
            return pizzaOrderDeliveredDb;
        };
    }

    @Bean
    public Consumer<String> complainToDelivery() {
        return lateOrderString -> {
            log.info("Cancelling Order :: -> {}", lateOrderString);
            JsonNode jsonNode = null;
            try {
                jsonNode = mapper.readTree(lateOrderString);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            PizzaOrder lateOrder = mapper.convertValue(jsonNode.get("variablesAsMap"), PizzaOrder.class);
            log.info("Late Order ::: {} ", lateOrder);
            PizzaOrder complainOrder = PizzaOrder.builder()
                    .name(lateOrder.getName())
                    .status(PizzaOrderStatus.COMPLAIN_RECEIVED)
                    .correlationKey(lateOrder.getCorrelationKey())
                    .createdAt(OffsetDateTime.now())
                    .modifiedAt(OffsetDateTime.now())
                    .build();
            PizzaOrder pizzaOrderComplainedDb = pizzaService.save(complainOrder);
            try {
                if (pizzaOrderComplainedDb.getId() % 2 == 0) {
                    TimeUnit.SECONDS.sleep(20L);
                    log.warn("PIZZA DELAYED for id [{}]", lateOrder.getId());
                } else {
                    log.warn("PIZZA RECEIVED for id [{}]", lateOrder.getId());
                    PizzaOrder deliveredOrder = PizzaOrder.builder()
                            .name(lateOrder.getName())
                            .status(PizzaOrderStatus.ORDER_LATE_DELIVERED)
                            .correlationKey(lateOrder.getCorrelationKey())
                            .createdAt(OffsetDateTime.now())
                            .modifiedAt(OffsetDateTime.now())
                            .build();
                    PizzaOrder pizzaOrderDeliveredDb = pizzaService.save(deliveredOrder);
                    kafkaProducer.send(deliveredOrder, "pizza-delivered");
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        };
    }

    @Bean
    public Function<String, PizzaOrder> cancelOrder() {
        return lateOrderString -> {
            log.info("Cancelling Order :: -> {}", lateOrderString);
            JsonNode jsonNode = null;
            try {
                jsonNode = mapper.readTree(lateOrderString);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            PizzaOrder lateOrder = mapper.convertValue(jsonNode.get("variablesAsMap"), PizzaOrder.class);
            log.info("ORDER CANCELLED :: {}", lateOrder);
            PizzaOrder cancelledOrder = PizzaOrder.builder()
                    .name(lateOrder.getName())
                    .status(PizzaOrderStatus.ORDER_CANCELLED)
                    .correlationKey(lateOrder.getCorrelationKey())
                    .createdAt(OffsetDateTime.now())
                    .modifiedAt(OffsetDateTime.now())
                    .build();
            PizzaOrder cancelledOrderDb = pizzaService.save(cancelledOrder);
            return cancelledOrderDb;
        };
    }

    @Bean
    public Function<String, PizzaOrder> processPizzaOrderComplete() {
        return deliveredOrder2 -> {
            log.info("Eat Pizza :: -> {}", deliveredOrder2);
            JsonNode jsonNode = null;
            try {
                jsonNode = mapper.readTree(deliveredOrder2);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            PizzaOrder deliveredOrder = mapper.convertValue(jsonNode.get("variablesAsMap"), PizzaOrder.class);
            PizzaOrder pizzaOrder = PizzaOrder.builder()
                    .name(deliveredOrder.getName())
                    .status(PizzaOrderStatus.ORDER_COMPLETED)
                    .correlationKey(deliveredOrder.getCorrelationKey())
                    .createdAt(OffsetDateTime.now())
                    .modifiedAt(OffsetDateTime.now())
                    .build();
            PizzaOrder completedPizzaOrder = pizzaService.save(pizzaOrder);
            log.info("Process Complete :: -> {}", completedPizzaOrder);
            return pizzaOrder;
        };
    }
}
