package com.camunda.pizza.producers;

import com.camunda.pizza.entity.PizzaOrder;
import com.camunda.pizza.model.TopicDetails;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

@Component
public class KafkaProducer {

    @Autowired
    private StreamBridge streamBridge;

    @Autowired
    private ObjectMapper mapper;
    public void sendMessage(TopicDetails topicDetails) {
        try {
            streamBridge.send(topicDetails.getTopicName(), mapper.writeValueAsString(topicDetails.getMessage()));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public void send(PizzaOrder pizzaOrder, String topicName) {
        try {
            streamBridge.send(topicName, mapper.writeValueAsString(pizzaOrder));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}