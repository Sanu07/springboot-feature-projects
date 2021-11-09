package com.admin.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ServiceExpert {

	private Long expertId;
	private String name;
	private Address address;
}
