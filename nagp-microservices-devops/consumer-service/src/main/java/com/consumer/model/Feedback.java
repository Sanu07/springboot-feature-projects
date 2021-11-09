package com.consumer.model;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Feedback {

	private Long id;
	private int ratingValue;
	private String desc;
	private LocalDateTime createdAt;
}
