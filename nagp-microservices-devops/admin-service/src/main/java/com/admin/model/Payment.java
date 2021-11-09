package com.admin.model;

import java.math.BigInteger;
import java.time.LocalDateTime;

import com.admin.enums.PaymentMode;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Payment {

	private Long id;
	private BigInteger amountPaidToVendor;
	private BigInteger amountOfShare;
	private boolean status;
	private PaymentMode mode;
	private LocalDateTime createdAt;
}
