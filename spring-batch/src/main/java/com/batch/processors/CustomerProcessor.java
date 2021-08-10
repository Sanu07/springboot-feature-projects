package com.batch.processors;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.persistence.EntityManager;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import com.batch.model.Customer;

public class CustomerProcessor implements ItemProcessor<Customer, Customer> {

	@Autowired
	private EntityManager em;

	private List<String> userIds = new ArrayList<>();

	@Override
	public Customer process(Customer customer) throws Exception {
		String initials[] = customer.getCustomerName().split(" ");
		customer.setCustomerId(initials[0].substring(0, 1).toUpperCase().concat(initials[1].substring(0, 1).toUpperCase())
				.concat(customer.getPhone()));
		customer.setCreatedByUserId(getUserId());
		return customer;
	}

	public String getUserId() {
		if (userIds.size() == 0) {
			userIds = em.createNativeQuery("SELECT LOGIN_ID FROM USER_DETAILS").getResultList();
		}
		int count = userIds.size();
		return userIds.get(new Random().nextInt(10) % count);
	}
}
