package com.vendor.model;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ConsumerReview {

	private Long id;
	private Long customerId;
	private Long vendorId;
	private Long expertId;
	private Long bookingId;
	private int ratingValue;
	private String feedback;
	private LocalDateTime timeStamp;

}
