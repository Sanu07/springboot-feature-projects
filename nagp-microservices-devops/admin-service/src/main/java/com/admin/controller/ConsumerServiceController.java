package com.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.admin.model.Customer;
import com.admin.service.proxy.ConsumerServiceProxy;

@RestController
public class ConsumerServiceController {

	@Autowired
	ConsumerServiceProxy consumer;
	
	@GetMapping("customers")
	public List<Customer> getAllCustomers() {
		return consumer.findAll();
	}
	
	@GetMapping("customers/{id}")
	public Customer getConsumer(@PathVariable Long id) {
		return consumer.findById(id);
	}
}
