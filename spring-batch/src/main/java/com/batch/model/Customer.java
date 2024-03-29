package com.batch.model;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import lombok.Data;

@Data
@Entity
@Table(name = "CUSTOMER_DETAILS")
public class Customer {

	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	@Column(name = "_ID", columnDefinition = "BINARY(16)")
	private UUID id;
	
	@Column(name = "CUSTOMER_ID")
	private String customerId;
	
	@Column(name = "CUSTOMER_NAME", nullable = false)
	private String customerName;

	@Column(name = "CUSTOMER_PHONE", unique = true, length = 10)
	private String phone;

	@Column(name = "CUSTOMER_EMAIL")
	private String email;

	@Column(name = "CUSTOMER_ADDRESS")
	private String address;
	
	@CreatedBy
	@Column(name = "CREATED_BY_USER_ID")
	private String createdByUserId;
	
	@Column(name = "IS_ACTIVE")
	private Boolean status;
	
	@CreationTimestamp
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@Column(name = "CREATED_AT")
	private LocalDateTime createdAt;
	
	@UpdateTimestamp
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@Column(name = "LAST_UPDATED_AT")
	private LocalDateTime updatedAt;
	
	@Version
	private int version;
	
	@PrePersist
	private void prePersist() {
		if (this.status == null) {
			this.status = true;
		}
	}
}
