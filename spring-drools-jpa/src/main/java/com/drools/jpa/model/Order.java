package com.drools.jpa.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Order {
    private Integer orderId;
    private BigDecimal price;
    private CardType cardType;
    private Double discount;
}
