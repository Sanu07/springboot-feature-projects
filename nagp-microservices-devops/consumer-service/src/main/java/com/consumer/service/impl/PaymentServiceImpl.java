package com.consumer.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.consumer.dao.impl.PaymentDaoImpl;
import com.consumer.model.Payment;
import com.consumer.service.PaymentService;

@Service
public class PaymentServiceImpl implements PaymentService {

	@Autowired
	PaymentDaoImpl repo;
	
	@Override
	public Payment save(Payment payment) {
		return repo.save(payment);
	}

	@Override
	public Payment findById(Long id) {
		return repo.findById(id);
	}

	@Override
	public List<Payment> findAll() {
		return repo.findAll();
	}

}
