package com.batch.converter;

import com.batch.mongo.model.Customer;
import com.batch.mongo.model.Feedback;
import com.batch.mongo.model.Order;

public class ObjectConverter {
	
	private ObjectConverter() {}

	public static Customer convertCustomerEntityToDto(com.batch.model.Customer c) {
		return Customer.builder()
				.address(c.getAddress())
				.createdAt(c.getCreatedAt())
				.createdByUserId(c.getCreatedByUserId())
				.customerId(c.getCustomerId())
				.customerName(c.getCustomerName())
				.email(c.getEmail())
				.id(c.getId().toString())
				.phone(c.getPhone())
				.status(c.getStatus())
				.updatedAt(c.getUpdatedAt())
				.version(c.getVersion())
				.build();
	}
	
	public static Order convertOrderEntityToDto(com.batch.model.Order o) {
		return Order.builder()
				.createdAt(o.getCreatedAt())
				.estimatedDeliveryDate(o.getEstimatedDeliveryDate())
				.id(o.getId().toString())
				.measurements(o.getMeasurements())
				.orderDeliveredOn(o.getOrderDeliveredOn())
				.orderId(o.getOrderId())
				.orderStatus(o.getOrderStatus())
				.status(o.getStatus())
				.updatedAt(o.getUpdatedAt())
				.version(o.getVersion())
				.build();
	}
	
	public static Feedback convertFeedbackEntityToDto(com.batch.model.Feedback f) {
		return Feedback.builder()
				.createdAt(f.getCreatedAt())
				.description(f.getDescription())
				.id(f.getId().toString())
				.rating(f.getRating())
				.status(f.getStatus())
				.version(f.getVersion())
				.build();
	}
}

