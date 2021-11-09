package com.consumer.model;

import com.consumer.enums.Service;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ServiceExpert {

	private Long expertId;
	private String name;
	private Address address;
	private Service service;
}
