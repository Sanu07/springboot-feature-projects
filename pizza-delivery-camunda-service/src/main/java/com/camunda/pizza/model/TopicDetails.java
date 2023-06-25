package com.camunda.pizza.model;

import lombok.Data;

@Data
public class TopicDetails {
    private String topicName;
    private TopicMessage message;
}
