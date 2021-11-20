package com.consumer.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.consumer.constants.AppConstants;
import com.consumer.enums.BookingStatus;
import com.consumer.model.BookingDetails;
import com.consumer.service.impl.BookingServiceImpl;
import com.consumer.service.impl.ConsumerServiceImpl;
import com.consumer.service.impl.KafkaProducerServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("consumers/bookings")
@Slf4j
public class BookingController {

	@Autowired
	BookingServiceImpl bookingService;
	
	@Autowired
	KafkaProducerServiceImpl kafkaService;
	
	@Autowired
	ConsumerServiceImpl customerService;
	
	@GetMapping
	public ResponseEntity<List<BookingDetails>> getAllBookingDetailss() {
		return ResponseEntity.ok(bookingService.findAll());
	}
	
	@GetMapping("{id}")
	public ResponseEntity<BookingDetails> getBookingDetails(@PathVariable Long id) {
		BookingDetails details = bookingService.findById(id);
		if (Objects.isNull(details)) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
		}
		return ResponseEntity.ok(details);
	}
	
	@PostMapping
	public ResponseEntity<BookingDetails> saveBookingDetails(@RequestBody BookingDetails booking) {
		if (Objects.isNull(booking)) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
		booking.setCustomer(customerService.findById((new Random().nextInt(10) % customerService.getTotalCustomers()) + 1L));
		booking.setStatus(BookingStatus.PENDING);
		booking.setBookingRaisedAt(LocalDateTime.now());
		BookingDetails requestedBookingDetails = bookingService.save(booking);
		ListenableFuture<SendResult<String, String>> sendEvent = null;
		try {
			sendEvent = kafkaService.sendEvent(AppConstants.CONSUMER_SERVICE_BOOKING_TOPIC, requestedBookingDetails.getId().toString(), requestedBookingDetails);
			if (sendEvent.get(5, TimeUnit.SECONDS) != null) {
				log.info("Booking {} has been processed successfully", requestedBookingDetails);
				return ResponseEntity.status(HttpStatus.CREATED).body(requestedBookingDetails);
			} else {
				requestedBookingDetails.setStatus(BookingStatus.CANCELLED);
				bookingService.save(requestedBookingDetails);
			}
		} catch (JsonProcessingException | InterruptedException | ExecutionException | TimeoutException e) {
			log.error("There was an error in processing with the booking. ", e);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(BookingDetails.builder().build());
	}
	
	@DeleteMapping
	public ResponseEntity<Void> deleteBookingDetails(@PathVariable Long id) {
		bookingService.deleteById(id);
		return ResponseEntity.status(HttpStatus.ACCEPTED).build();
	}
}
