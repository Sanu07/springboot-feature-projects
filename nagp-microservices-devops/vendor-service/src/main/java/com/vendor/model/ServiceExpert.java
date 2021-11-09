package com.vendor.model;

import com.vendor.enums.Service;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ServiceExpert {

	private Long expertId;
	private String name;
	private Address address;
	private Service profession;
}
