package com.batch.mongo.model;

import java.time.LocalDateTime;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;

import lombok.Data;

@Data
@Document
public class User {

	@Id
	private String id;

	private String fullName;

	private String password;

	private String email;

	private String loginId;

	private String phone;
	
	private String imageFilePath;
	
	private boolean status;

	private int version;
	
	private byte[] file;

	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	private LocalDateTime createdAt;

	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	private LocalDateTime updatedAt;

}
