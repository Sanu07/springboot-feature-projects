package com.consumer.model;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VendorResponse {

	private Long bookingId;
	private ServiceExpert expert;
	private LocalDateTime requestAcceptedAt;
	private boolean isAccepted;
}
