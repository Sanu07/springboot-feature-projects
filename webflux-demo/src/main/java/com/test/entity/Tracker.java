package com.test.entity;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class Tracker {

	private Integer trackerId;
	private String trackerName;
	
	@Getter(value = AccessLevel.NONE)
	@Setter(value = AccessLevel.NONE)
	private String trackerDescription;

	@JsonGetter("tracker-description-renamed")
	public String getTrackerDescription() {
		return trackerDescription;
	}

	@JsonSetter("trackerDescription")
	public void setTrackerDescription(String trackerDescription) {
		this.trackerDescription = trackerDescription;
	}

}
