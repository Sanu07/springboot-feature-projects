package com.consumer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.consumer.model.BookingDetails;
import com.consumer.service.impl.BookingServiceImpl;

@RestController
@RequestMapping("bookings")
public class BookingController {

	@Autowired
	BookingServiceImpl service;
	
	@GetMapping
	public ResponseEntity<List<BookingDetails>> getAllBookingDetailss() {
		return ResponseEntity.ok(service.findAll());
	}
	
	@GetMapping("{id}")
	public ResponseEntity<BookingDetails> getBookingDetails(@PathVariable Long id) {
		return ResponseEntity.ok(service.findById(id));
	}
	
	@PostMapping
	public ResponseEntity<BookingDetails> saveBookingDetails(@RequestBody BookingDetails booking) {
		return ResponseEntity.status(HttpStatus.CREATED).body(service.save(booking));
	}
	
	@DeleteMapping
	public ResponseEntity<Void> deleteBookingDetails(@PathVariable Long id) {
		service.deleteById(id);
		return ResponseEntity.status(HttpStatus.ACCEPTED).build();
	}
}
