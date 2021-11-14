package com.admin.service;

import java.util.List;

import com.admin.model.BookingDetails;

public interface BookingService {

	BookingDetails save(BookingDetails booking);
	BookingDetails findById(Long id);
	List<BookingDetails> findAll();
}
