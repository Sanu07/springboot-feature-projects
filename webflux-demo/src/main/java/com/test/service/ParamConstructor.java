package com.test.service;

import org.springframework.stereotype.Service;

import com.test.entity.Status;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ParamConstructor {

	private Status status;
	private String sheetName;

	ParamConstructor(Status status, String sheetName) {
		this.status = status;
		this.sheetName = sheetName;
	}
	
	ParamConstructor() {
		
	}

	public boolean isValid(String field, String value, String sheetName) {
		System.out.println(this.status);
		System.out.println(this.sheetName);
		if (this.status.getField().equalsIgnoreCase(field) && this.status.getValue().equalsIgnoreCase(value)
				&& this.sheetName.equalsIgnoreCase(sheetName)) {
			return true;
		}
		return false;
	}
}
