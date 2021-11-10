package com.vendor.model;

import java.math.BigInteger;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PackageRating {

	private Long id;
	private float value;
	private BigInteger totalRatings;
}
