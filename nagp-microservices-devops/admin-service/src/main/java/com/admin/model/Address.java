package com.admin.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Address {

	private Long id;
	private String addressLine;
	private String city;
	private String state;
	private String country;
	private Long pinCode; 
}
