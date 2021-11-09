package com.vendor.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Customer {

	private Long id;
	private Address address;
	private String name;
	private String phone;
}
