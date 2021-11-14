package com.consumer.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.consumer.dao.impl.CustomerDaoImpl;
import com.consumer.model.Customer;
import com.consumer.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	CustomerDaoImpl repo;
	
	@Override
	public Customer save(Customer customer) {
		return repo.save(customer);
	}

	@Override
	public Customer findById(Long id) {
		return repo.findById(id);
	}

	@Override
	public List<Customer> findAll() {
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
