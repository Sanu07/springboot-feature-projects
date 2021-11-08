package com.test.entity;

import java.util.List;
import java.util.Map;

import javax.annotation.Generated;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.annotation.Version;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(value = "NRI")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NRI {

	@Id
	private Long id;
	
//	@JsonProperty("my-trackers")
//	private List<Tracker> trackers;
//	
//	private Map<String, Milestone> milestones;
	
	private String name;
	@Version
	private long version;
}
