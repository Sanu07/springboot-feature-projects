package com.vendor.service;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.vendor.model.BookingDetails;
import com.vendor.model.Feedback;

@FeignClient(name = "consumer-service")
public interface ConsumerServiceProxy {

	@GetMapping("consumers/feedbacks")
	public List<Feedback> findAllFeedbacks();

	@GetMapping("consumers/bookings/{id}")
	public ResponseEntity<BookingDetails> getBookingDetails(@PathVariable(value = "id") Long id);

}
