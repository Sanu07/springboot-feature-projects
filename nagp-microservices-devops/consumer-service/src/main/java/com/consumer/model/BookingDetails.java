package com.consumer.model;

import java.time.LocalDateTime;
import java.util.List;

import com.consumer.enums.Service;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookingDetails {

	private Long id;
	private ServiceExpert serviceExpert;
	private LocalDateTime bookingRaisedAt;
	private boolean acceptanceStatus;
	private List<String> services;
	private Feedback feedback;
	private Payment payment;
}
