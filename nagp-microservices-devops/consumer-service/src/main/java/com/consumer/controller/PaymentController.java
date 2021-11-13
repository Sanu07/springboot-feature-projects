package com.consumer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.consumer.model.Payment;
import com.consumer.service.impl.PaymentServiceImpl;

@RestController
@RequestMapping("payments")
public class PaymentController {

	@Autowired
	PaymentServiceImpl service;
	
	@GetMapping
	public ResponseEntity<List<Payment>> getAllPayments() {
		return ResponseEntity.ok(service.findAll());
	}
	
	@GetMapping("{id}")
	public ResponseEntity<Payment> getPayment(@PathVariable Long id) {
		return ResponseEntity.ok(service.findById(id));
	}
	
	@PostMapping
	public ResponseEntity<Payment> savePayment(@RequestBody Payment payment) {
		return ResponseEntity.status(HttpStatus.CREATED).body(service.save(payment));
	}
	
}
