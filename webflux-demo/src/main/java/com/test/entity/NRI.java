package com.test.entity;

import java.util.List;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;

@Table(value = "NRI")
@Data
@Builder
public class NRI {

	@Id
	private Long id;
	
	@JsonProperty("my-trackers")
	private List<Tracker> trackers;
	
	private Map<String, Milestone> milestones;
}
