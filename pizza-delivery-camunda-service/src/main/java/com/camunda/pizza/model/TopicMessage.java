package com.camunda.pizza.model;

import com.camunda.pizza.enums.PizzaOrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TopicMessage {
    private String name;
    private PizzaOrderStatus status;
    private String correlationKey;
}
