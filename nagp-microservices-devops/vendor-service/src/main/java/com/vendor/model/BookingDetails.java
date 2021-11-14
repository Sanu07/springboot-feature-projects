package com.vendor.model;

import java.time.LocalDateTime;
import java.util.List;

import com.vendor.enums.Service;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookingDetails {

	private Long id;
	private Customer customer;
	private LocalDateTime timeStamp;
	private boolean acceptanceStatus;
	private List<Service> services;
	private ServiceExpert expert;
	private boolean isAccepted;
}
