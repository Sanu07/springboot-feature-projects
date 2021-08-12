package com.batch.processors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import com.batch.model.Customer;
import com.batch.model.Feedback;
import com.batch.model.Order;

public class FeedbackProcessor implements ItemProcessor<Feedback, Feedback> {

	@Autowired
	private EntityManager em;

	private List<Customer> customers = new ArrayList<>();
	
	private List<Order> orders = new ArrayList<>();
	
	Map<UUID, Customer> customerMap = new HashMap<>();
	
	private int counter;
	
	private final String[] descriptions = {
			"It was not upto my expectations",
			"It was ok. I was expecting something better",
			"I liked it, but it was way too costly",
			"It was very good",
			"It was awesome"
	};
	
	@Override
	public Feedback process(Feedback feedback) throws Exception {
		int count = descriptions.length;
		int pos = (new Random().nextInt(100) % count);
		feedback.setDescription(descriptions[pos]);
		feedback.setRating(pos + 1);
		Order order = getOrder();
		feedback.setOrder(order);
		feedback.setCustomer(getCustomer(order.getCustomer().getId()));
		return feedback;
	}

	public Customer getCustomer(UUID customerId) {
		if (customers.size() == 0) {
			customers = em.createNativeQuery("SELECT * FROM CUSTOMER_DETAILS", Customer.class).getResultList();
		}
		customerMap = customers.stream().collect(Collectors.toMap(Customer::getId, Function.identity()));
		return customerMap.get(customerId);
	}
	
	public Order getOrder() {
		if (orders.size() == 0) {
			orders = em.createNativeQuery("SELECT * FROM ORDER_DETAILS", Order.class).getResultList();
		}
		int count = orders.size();
		return orders.get(counter++);
	}
}
