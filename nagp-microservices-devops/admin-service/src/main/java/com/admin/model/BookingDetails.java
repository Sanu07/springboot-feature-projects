package com.admin.model;

import java.time.LocalDateTime;
import java.util.List;

import com.admin.enums.Service;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookingDetails {

	private Long id;
	private LocalDateTime bookingRaisedAt;
	private LocalDateTime bookingAcceptedAt;
	private List<Service> services;
	private Consumer customer;
	private ServiceExpert serviceExpert;
}
