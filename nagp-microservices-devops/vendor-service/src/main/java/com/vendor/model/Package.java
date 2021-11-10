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
	private List<IncludedService> includedServices;
}
