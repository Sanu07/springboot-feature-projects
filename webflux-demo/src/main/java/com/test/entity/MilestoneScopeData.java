package com.test.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MilestoneScopeData {

	private int id;
	private String field;
	private String vallue;
}
