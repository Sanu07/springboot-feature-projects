package com.consumer.model;

import java.util.List;

import com.consumer.enums.Service;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ServiceExpert {

	private Long id;
	private String name;
	private Address address;
	private List<Service> services;
}
