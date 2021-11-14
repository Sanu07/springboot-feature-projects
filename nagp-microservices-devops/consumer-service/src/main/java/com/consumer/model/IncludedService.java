package com.consumer.model;

import java.math.BigInteger;
import java.util.List;

import com.consumer.enums.Service;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class IncludedService {

	private Long id;
	List<String> descriptions;
	private Service service;
	private BigInteger price;
}
