package com.consumer.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Consumer {

	private Long id;
	private Address address;
	private String name;
	private String phone;
}
