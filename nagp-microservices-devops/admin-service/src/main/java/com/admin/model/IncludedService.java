package com.admin.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class IncludedService {

	private Long id;
	private String description;
}
