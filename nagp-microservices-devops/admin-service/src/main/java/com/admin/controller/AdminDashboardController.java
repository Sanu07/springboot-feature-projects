package com.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.admin.model.BookingDetails;
import com.admin.model.Customer;
import com.admin.model.ServiceExpert;
import com.admin.proxy.service.ConsumerServiceProxy;
import com.admin.proxy.service.VendorServiceProxy;
import com.admin.service.impl.BookingServiceImpl;

@RestController
public class AdminDashboardController {

	@Autowired
	ConsumerServiceProxy consumer;
	
	@Autowired
	VendorServiceProxy vendorProxy;
	
	@Autowired
	BookingServiceImpl bookingService;
	
	@GetMapping("customers")
	public ResponseEntity<List<Customer>> getAllCustomers() {
		return ResponseEntity.ok(consumer.findAll());
	}
	
	@GetMapping("customers/{id}")
	public Customer getConsumer(@PathVariable Long id) {
		return consumer.findById(id);
	}
	
	@GetMapping("admin/bookingsHistory")
	public ResponseEntity<List<BookingDetails>> getAllBookings() {
		return ResponseEntity.ok(bookingService.findAll());
	}
	
	@GetMapping("admin/vendors")
	public ResponseEntity<List<ServiceExpert>> getAllVendors() {
		return ResponseEntity.ok(vendorProxy.findAll());
	}
}
