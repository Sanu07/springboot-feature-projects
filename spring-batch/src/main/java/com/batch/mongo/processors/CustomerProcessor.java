package com.batch.mongo.processors;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import com.batch.converter.ObjectConverter;
import com.batch.model.Customer;
import com.batch.mongo.model.Feedback;
import com.batch.mongo.model.Order;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomerProcessor implements ItemProcessor<Customer, com.batch.mongo.model.Customer> {

	@Autowired
	private EntityManager em;

	@Override
	public com.batch.mongo.model.Customer process(Customer customer) throws Exception {
		com.batch.mongo.model.Customer mongoCustomer = ObjectConverter.convertCustomerEntityToDto(customer);
		mongoCustomer.setOrders(getOrdersByCustomer(customer));
		return mongoCustomer;
	}

	private Set<Order> getOrdersByCustomer(Customer customer) {
		List<com.batch.model.Order> resultList = em
				.createNativeQuery("SELECT * FROM ORDER_DETAILS WHERE CUSTOMER_ID = :ID", com.batch.model.Order.class)
				.setParameter("ID", customer.getId()).getResultList();
		Set<Order> orders = resultList.stream().map(o -> {
			Order mongoOrder = ObjectConverter.convertOrderEntityToDto(o);
			mongoOrder.setCustomerId(customer.getCustomerId());
			mongoOrder.setFeedback(getFeedbackByOrderId(o, customer));
			return mongoOrder;
		}).collect(Collectors.toSet());
		return orders;
	}

	private Feedback getFeedbackByOrderId(com.batch.model.Order order, Customer customer) {
		Feedback feedback = null;
		try {
			com.batch.model.Feedback result = (com.batch.model.Feedback) em
					.createNativeQuery("SELECT * FROM FEEDBACK_DETAILS WHERE ORDER_ID = :ID",
							com.batch.model.Feedback.class)
					.setParameter("ID", order.getId()).getSingleResult();
			feedback = ObjectConverter.convertFeedbackEntityToDto(result);
		} catch (Exception e) {
			log.info("No entity with order_id = " + order.getId());
			return null;
		}
		feedback.setOrderId(order.getOrderId());
		feedback.setCustomerId(customer.getCustomerId());
		return feedback;
	}

}
