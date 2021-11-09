package com.vendor.model;

import java.math.BigInteger;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Rating {

	private Long id;
	private int value;
	private BigInteger totalRatings;
}
