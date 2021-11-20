package com.admin.proxy.service;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.admin.model.Consumer;
import com.admin.model.Feedback;
import com.admin.model.Payment;

@FeignClient(name = "consumer-service")
public interface ConsumerServiceProxy {

	@GetMapping("consumers")
	public List<Consumer> findAll();
	
	@GetMapping("consumers/{id}")
	public Consumer findById(@PathVariable(value = "id") Long id);
	
	@GetMapping("consumers/payments")
	public ResponseEntity<List<Payment>> getAllPayments();
	
	@GetMapping("consumers/feedbacks")
	public ResponseEntity<List<Feedback>> getAllFeedbacks();
	
}
