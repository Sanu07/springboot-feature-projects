package com.admin.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Admin {

	private Long id;
	private String name;
	private String phone;
	private Address address;
}
