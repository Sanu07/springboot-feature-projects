package com.consumer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.consumer.model.Customer;
import com.consumer.service.impl.CustomerServiceImpl;

@RestController
@RequestMapping("customers")
public class CustomerController {

	@Autowired
	CustomerServiceImpl service;
	
	@GetMapping
	public ResponseEntity<List<Customer>> getAllCustomers() {
		return ResponseEntity.ok(service.findAll());
	}
	
	@GetMapping("{id}")
	public ResponseEntity<Customer> getCustomer(@PathVariable Long id) {
		return ResponseEntity.ok(service.findById(id));
	}
	
	@PostMapping
	public ResponseEntity<Customer> saveCustomer(@RequestBody Customer customer) {
		return ResponseEntity.status(HttpStatus.CREATED).body(service.save(customer));
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
		service.deleteById(id);
		return ResponseEntity.status(HttpStatus.ACCEPTED).build();
	}
}
