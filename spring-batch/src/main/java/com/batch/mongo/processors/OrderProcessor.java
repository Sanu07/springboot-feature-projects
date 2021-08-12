package com.batch.mongo.processors;

import javax.persistence.EntityManager;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import com.batch.converter.ObjectConverter;
import com.batch.model.Order;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OrderProcessor implements ItemProcessor<Order, com.batch.mongo.model.Order> {

	@Autowired
	private EntityManager em;

	@Override
	public com.batch.mongo.model.Order process(Order order) throws Exception {
		com.batch.mongo.model.Order mongoOrder = ObjectConverter.convertOrderEntityToDto(order);
		mongoOrder.setCustomerId(order.getCustomer().getId().toString());
		return mongoOrder;
	}
}
