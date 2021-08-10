package com.batch.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import com.batch.util.MeasurementsConverter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import lombok.Data;

@Data
@Entity
@Table(name = "ORDER_DETAILS")

public class Order {

	@Column(name = "ORDER_ID")
	private String orderId;

	@Column(name = "ESTIMATED_DELIVERY_DATE")
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime estimatedDeliveryDate;

	@Column(name = "ORDER_DELIVERED_ON")
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime orderDeliveredOn;

	@Enumerated(EnumType.STRING)
	@Column(name = "STATUS", nullable = false)
	private OrderStatusEnum orderStatus;

	@Convert(converter = MeasurementsConverter.class)
	@Column(name = "MEASUREMENTS")
	private List<Measurements> measurements;
	
	@Column(name = "TOTAL_AMOUNT")
	private Double totalAmount;
	
	@ManyToOne
	@JoinColumn(name = "customer_id")
	private Customer customer;

	@OneToOne(mappedBy = "order")
	@JsonIgnore
	private Feedback feedback;
	
	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	@Column(name = "_ID", columnDefinition = "BINARY(16)")
	private UUID id;
	
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
