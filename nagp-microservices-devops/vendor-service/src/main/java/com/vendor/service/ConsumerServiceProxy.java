package com.vendor.service;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.vendor.model.BookingDetails;
import com.vendor.model.Feedback;

@FeignClient(name = "consumer-service")
public interface ConsumerServiceProxy {

	@GetMapping("feedbacks")
	public List<Feedback> findAllFeedbacks();
	
	@GetMapping("bookings")
	public List<BookingDetails> findAllBookings();
	
}
