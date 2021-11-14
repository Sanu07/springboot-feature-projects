package com.consumer.model;

import java.math.BigInteger;
import java.time.LocalDateTime;

import com.consumer.enums.PaymentMode;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Payment {

	private Long id;
	private Long bookingId;
	private BigInteger amountPaid;
	private BigInteger dueAmount;
	private LocalDateTime paidAt;
	private boolean paymentStatus;
	private PaymentMode mode;
}
