package com.consumer.service;

import java.util.List;

import com.consumer.model.Payment;

public interface PaymentService {

	Payment save(Payment customer);
	Payment findById(Long id);
	List<Payment> findAll();
}
