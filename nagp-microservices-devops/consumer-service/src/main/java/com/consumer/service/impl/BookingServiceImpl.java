package com.consumer.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.consumer.dao.impl.BookingDaoImpl;
import com.consumer.model.BookingDetails;
import com.consumer.service.BookingService;

@Service
public class BookingServiceImpl implements BookingService {

	@Autowired
	BookingDaoImpl repo;
	
	@Override
	public BookingDetails save(BookingDetails booking) {
		return repo.save(booking);
	}

	@Override
	public BookingDetails findById(Long id) {
		return repo.findById(id);
	}

	@Override
	public List<BookingDetails> findAll() {
		return repo.findAll();
	}

	@Override
	public void deleteById(Long id) {
		repo.deleteById(id);
	}

}
