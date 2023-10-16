package com.shared.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    private String orderId;
    private Integer customerId;
    private Integer sellerId;
    private Product product;
    private String paymentMethod;
    private String deliveryLocation;
    private String orderStatus;
    private String createdTimestamp;
    private String updatedTimestamp;
}