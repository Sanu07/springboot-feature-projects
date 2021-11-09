package com.vendor.model;

import java.math.BigInteger;
import java.util.List;

import com.vendor.enums.Category;
import com.vendor.enums.Service;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Package {

	private Long id;
	private Category category;
	private Service profession;
	private BigInteger price;
	private List<IncludedService> includedServices;
	private List<CustomerReview> reviews;
	private Rating rating;
}
