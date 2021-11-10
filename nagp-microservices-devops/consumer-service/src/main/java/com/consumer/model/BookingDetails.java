package com.consumer.model;

import java.time.LocalDateTime;
import java.util.List;

import com.consumer.enums.BookingStatus;
import com.consumer.enums.Service;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookingDetails {

	private Long id;
	private LocalDateTime bookingRaisedAt;
	private List<Service> services;
	private Long feedbackId;
	private Long paymentId;
	private Long customerId;
	private Long serviceExpertId;
	private BookingStatus status;
}
