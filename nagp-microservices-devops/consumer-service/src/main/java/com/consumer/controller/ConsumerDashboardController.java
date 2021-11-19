package com.consumer.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.consumer.model.BookingDetails;
import com.consumer.model.Package;
import com.consumer.model.Payment;
import com.consumer.service.VendorServiceProxy;
import com.consumer.service.impl.BookingServiceImpl;
import com.consumer.service.impl.CustomerServiceImpl;
import com.consumer.service.impl.PaymentServiceImpl;

@RestController
@RequestMapping("consumers")
public class ConsumerDashboardController {

	@Autowired
	CustomerServiceImpl customerService;

	@Autowired
	PaymentServiceImpl paymentService;

	@Autowired
	BookingServiceImpl bookingService;

	@Autowired
	VendorServiceProxy vendorServiceProxy;

	@GetMapping("paymentHistory/{consumerId}")
	public ResponseEntity<List<Payment>> getPaymentsHistory(@PathVariable Long consumerId) {
		List<BookingDetails> bookingDetails = bookingService.findAll();
		List<BookingDetails> customerBookingDetails = bookingDetails.stream()
				.filter(bk -> bk.getCustomer().getId().equals(consumerId)).collect(Collectors.toList());
		List<Payment> payments = paymentService.findAll();
		List<Payment> paymentHistory = payments.stream()
				.filter(payment -> customerBookingDetails.stream()
						.anyMatch(custBk -> custBk.getId().equals(payment.getBookingId())))
				.collect(Collectors.toList());
		return ResponseEntity.ok(paymentHistory);
	}

	@GetMapping("listPackages")
	public ResponseEntity<List<Package>> getAllPackages() {
		return ResponseEntity.ok(vendorServiceProxy.findAllPackages());
	}

	@GetMapping("bookingHistory/{consumerId}")
	public ResponseEntity<List<BookingDetails>> getBookingDetails(@PathVariable Long consumerId) {
		List<BookingDetails> bookings = bookingService.findAll();
		List<BookingDetails> customerBookingHistory = bookings.stream()
				.filter(bk -> bk.getCustomer().getId().equals(consumerId)).collect(Collectors.toList());
		return ResponseEntity.ok(customerBookingHistory);
	}
}
