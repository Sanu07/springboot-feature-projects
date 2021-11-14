package com.vendor.model;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Feedback {

	private Long id;
	private Long bookingId;
	private int ratingValue;
	private String description;
	private LocalDateTime createdAt;
}
