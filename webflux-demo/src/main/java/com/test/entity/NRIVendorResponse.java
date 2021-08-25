package com.test.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class NRIVendorResponse {

	private String response;
	private Object body;
	@JsonInclude(value = Include.NON_NULL)
	private String errorMessage;
}
