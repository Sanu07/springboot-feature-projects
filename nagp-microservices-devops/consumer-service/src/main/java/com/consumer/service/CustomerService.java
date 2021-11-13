package com.consumer.service;

import java.util.List;

import com.consumer.model.Customer;

public interface CustomerService {

	Customer save(Customer customer);
	Customer findById(Long id);
	List<Customer> findAll();
	void deleteById(Long id);
}
