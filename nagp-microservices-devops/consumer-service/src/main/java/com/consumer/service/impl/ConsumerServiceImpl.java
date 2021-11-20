package com.consumer.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.consumer.dao.impl.ConsumerDaoImpl;
import com.consumer.model.Consumer;
import com.consumer.service.ConsumerService;

@Service
public class ConsumerServiceImpl implements ConsumerService {

	@Autowired
	ConsumerDaoImpl repo;
	
	@Override
	public Consumer save(Consumer customer) {
		return repo.save(customer);
	}

	@Override
	public Consumer findById(Long id) {
		return repo.findById(id);
	}

	@Override
	public List<Consumer> findAll() {
		return repo.findAll();
	}

	@Override
	public void deleteById(Long id) {
		repo.deleteById(id);
	}

	public int getTotalCustomers() {
		return repo.getSize();
	}
}
