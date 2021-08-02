package com.test.entity;

import java.time.LocalDate;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.test.config.CustomDateDeserializer;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class Milestone {

	private LocalDate date1;
	
	@Getter(value = AccessLevel.NONE)
	@Setter(value = AccessLevel.NONE)
	private LocalDate date2;
	
	@Getter(value = AccessLevel.NONE)
	@Setter(value = AccessLevel.NONE)
	private boolean approvalStatus;

	@JsonGetter("approval-status-renamed")
	public boolean isApprovalStatus() {
		return approvalStatus;
	}

	@JsonSetter("approvalStatus")
	public void setApprovalStatus(boolean approvalStatus) {
		this.approvalStatus = approvalStatus;
	}

	@JsonDeserialize(using = CustomDateDeserializer.class)
	@JsonFormat(shape = Shape.STRING, pattern = "MM/dd/yyyy")
	public LocalDate getDate2() {
		return date2;
	}

	public void setDate2(LocalDate date2) {
		this.date2 = date2;
	}
}
