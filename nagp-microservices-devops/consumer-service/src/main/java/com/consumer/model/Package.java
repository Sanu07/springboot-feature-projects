package com.consumer.model;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Package {

	private Long id;
	private List<IncludedService> includedServices;
}
