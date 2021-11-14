package com.vendor.model;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VendorNotifications {

	private BookingDetails bookingDetails;
	private List<ServiceExpert> experts;
	private LocalDateTime createdAt;
	private LocalDateTime responseReceivedAt;
}