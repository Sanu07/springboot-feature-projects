package com.consumer.service;

import java.util.List;

import com.consumer.model.Consumer;

public interface ConsumerService {

	Consumer save(Consumer customer);
	Consumer findById(Long id);
	List<Consumer> findAll();
	void deleteById(Long id);
}
