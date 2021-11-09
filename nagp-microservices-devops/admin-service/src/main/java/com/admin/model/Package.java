package com.admin.model;

import java.math.BigInteger;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Package {

	private Long id;
	private String category;
	private String profession;
	private BigInteger price;
	private List<IncludedService> includedServices;
}
