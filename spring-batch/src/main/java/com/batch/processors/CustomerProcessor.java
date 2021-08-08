package com.batch.processors;

import java.util.Random;

import org.springframework.batch.item.ItemProcessor;

import com.batch.model.Customer;

public class CustomerProcessor implements ItemProcessor<Customer, Customer> {

	@Override
	public Customer process(Customer customer) throws Exception {
		String customerIdInitials = customer.getFirstName().substring(0, 1) + customer.getLastName().substring(0, 1);
		String customerIdFakePhoneNo = getFakePhoneNo();
		customer.setCustomerId(customerIdInitials.toUpperCase().concat(customerIdFakePhoneNo));
		return customer;
	}

	public String getFakePhoneNo() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 10; i++) {
			sb.append(new Random().nextInt(10));
		}
		return sb.toString();
	}
	
}
