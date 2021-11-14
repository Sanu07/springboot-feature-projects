package com.admin.model;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VendorNotifications {

	private BookingDetails bookingDetails;
	private List<ServiceExpert> experts;
}
