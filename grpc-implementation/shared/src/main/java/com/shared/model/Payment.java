package com.shared.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

    private String paymentId;

    private String orderId;
    private Integer customerId;
    private Integer sellerId;
    private String paymentMethod;
    private String paymentStatus;
    private String paymentStatusNotes;
}