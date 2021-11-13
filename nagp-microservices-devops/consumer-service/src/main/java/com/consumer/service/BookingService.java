package com.consumer.service;

import java.util.List;

import com.consumer.model.BookingDetails;

public interface BookingService {

	BookingDetails save(BookingDetails booking);
	BookingDetails findById(Long id);
	List<BookingDetails> findAll();
	void deleteById(Long id);
}
