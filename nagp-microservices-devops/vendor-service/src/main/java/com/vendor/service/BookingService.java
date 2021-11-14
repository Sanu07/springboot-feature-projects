package com.vendor.service;

import java.util.List;

import com.vendor.model.BookingDetails;

public interface BookingService {

	BookingDetails save(BookingDetails booking);
	BookingDetails findById(Long id);
	List<BookingDetails> findAll();
	void deleteById(Long id);
}
