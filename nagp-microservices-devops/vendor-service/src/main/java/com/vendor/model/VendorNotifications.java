package com.vendor.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VendorNotifications {

	private BookingDetails bookingDetails;
	private ServiceExpert experts;
}
