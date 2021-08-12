package com.batch.mongo.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;

import com.batch.model.Measurements;
import com.batch.model.OrderStatusEnum;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "order_details")
public class Order {

	@Id
	private String id;

	private String orderId;

	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	private LocalDateTime createdAt;

	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	private LocalDateTime estimatedDeliveryDate;

	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	private LocalDateTime orderDeliveredOn;

	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	private LocalDateTime updatedAt;

	@Enumerated(EnumType.STRING)
	private OrderStatusEnum orderStatus;
	
	private boolean status;
	
	private int version;
	
	private String customerId;

    private List<Measurements> measurements;
	
	// private Set<Payment> payments;
	
	private Feedback feedback;

}
