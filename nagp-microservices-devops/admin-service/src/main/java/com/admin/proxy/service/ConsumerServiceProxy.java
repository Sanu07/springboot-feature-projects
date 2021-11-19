package com.admin.proxy.service;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.admin.model.Customer;
import com.admin.model.Feedback;
import com.admin.model.Payment;

@FeignClient(name = "consumer-service")
public interface ConsumerServiceProxy {

	@GetMapping("customers")
	public List<Customer> findAll();
	
	@GetMapping("customers/{id}")
	public Customer findById(@PathVariable(value = "id") Long id);
	
	@GetMapping("payments")
	public ResponseEntity<List<Payment>> getAllPayments();
	
	@GetMapping("feedbacks")
	public ResponseEntity<List<Feedback>> getAllFeedbacks();
	
}
