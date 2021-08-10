package com.batch.processors;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import javax.persistence.EntityManager;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.WordUtils;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import com.batch.model.Customer;
import com.batch.model.Measurements;
import com.batch.model.Order;
import com.batch.model.OrderStatusEnum;

public class OrderProcessor implements ItemProcessor<Order, Order> {

	@Autowired
	private EntityManager em;

	private List<Customer> customers = new ArrayList<>();

	@Override
	public Order process(Order order) throws Exception {
		//orderstatus
		// measurements
		//orderid
		// customer
		Customer customer = getCustomer();
		order.setOrderId(getGeneratedIdOrderId(customer.getCustomerName()));
		order.setOrderStatus(OrderStatusEnum.CREATED);
		order.setCustomer(customer);
		order.setMeasurements(Arrays.asList(getMeasurements("height"), getMeasurements("length")));
		order.setEstimatedDeliveryDate(LocalDateTime.now());
		return order;
	}

	public Measurements getMeasurements(String name) {
		return Measurements.builder().amount(new Random().nextInt(1000) + 1.0).name(name).size(new Random().nextInt(100) + 1 + " cm")
				.build();
	}

	public Customer getCustomer() {
		if (customers.size() == 0) {
			customers = em.createNativeQuery("SELECT * FROM CUSTOMER_DETAILS", Customer.class).getResultList();
		}
		int count = customers.size();
		return customers.get(new Random().nextInt(100) % count);
	}

	public String getGeneratedIdOrderId(String customerName) {
		Long randomTenDigNumber = 10000000L + ThreadLocalRandom.current().nextInt(1, 100) * 37;
		return StringUtils.join(WordUtils.initials(customerName, null), "OR" + String.valueOf(randomTenDigNumber));
	}
}
