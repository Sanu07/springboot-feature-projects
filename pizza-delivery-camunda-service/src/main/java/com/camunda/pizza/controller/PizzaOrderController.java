package com.camunda.pizza.controller;

import com.camunda.pizza.entity.PizzaOrder;
import com.camunda.pizza.model.TopicDetails;
import com.camunda.pizza.producers.KafkaProducer;
import com.camunda.pizza.service.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("pizza")
@RestController
public class PizzaOrderController {

    @Autowired
    private PizzaService pizzaService;

    @Autowired
    private KafkaProducer kafkaProducer;

    @GetMapping("orders")
    public List<PizzaOrder> getPizzaOrders() {
        return pizzaService.getPizzaOrders();
    }

    @GetMapping("orders/{key}")
    public List<PizzaOrder> getPizzaOrdersByCorrelationKey(@PathVariable("key") String key) {
        return pizzaService.getPizzaOrderByCorrelationKey(key);
    }

    @PostMapping("orders")
    public ResponseEntity<Void> postMessage(@RequestBody TopicDetails topicDetails) {
        kafkaProducer.sendMessage(topicDetails);
        return ResponseEntity.accepted().build();
    }
}
