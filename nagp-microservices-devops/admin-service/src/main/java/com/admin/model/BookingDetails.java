package com.admin.model;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookingDetails {

	private Long id;
	private Customer customer;
	private ServiceExpert serviceExpert;
	private Payment paymnent;
	private LocalDateTime bookingRaisedAt;
	private LocalDateTime bookingAcceptedAt;
}
