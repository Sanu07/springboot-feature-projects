package com.admin.controller;

import java.util.List;
import java.util.Optional;

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

import com.admin.constants.AppConstants;
import com.admin.model.Admin;
import com.admin.model.BookingDetails;
import com.admin.model.ServiceExpert;
import com.admin.model.VendorNotifications;
import com.admin.service.impl.AdminServiceImpl;
import com.admin.service.impl.BookingServiceImpl;

@RestController
@RequestMapping("admins")
public class AdminController {

	@Autowired
	private AdminServiceImpl service;

	@Autowired
	BookingServiceImpl bookingService;

	@GetMapping
	public ResponseEntity<List<Admin>> getAllAdmins() {
		return ResponseEntity.ok(service.findAll());
	}

	@GetMapping("{id}")
	public ResponseEntity<Admin> getAdmin(@PathVariable Long id) {
		return ResponseEntity.ok(service.findById(id));
	}

	@PostMapping
	public ResponseEntity<Admin> saveAdmin(@RequestBody Admin admin) {
		return ResponseEntity.ok(service.save(admin));
	}

	@DeleteMapping("{id}")
	public ResponseEntity<Void> deleteAdmin(@PathVariable Long id) {
		service.deleteById(id);
		return ResponseEntity.status(HttpStatus.ACCEPTED).build();
	}

	@PostMapping("checkBookingsWithNoResponseStatus")
	public void checkForNoResponseBookings(@RequestBody ServiceExpert expert) {
		List<BookingDetails> bookings = bookingService.findAll();
		Optional<BookingDetails> booking = bookings.stream()
				.filter(b -> b.getStatus().equalsIgnoreCase(AppConstants.NO_RESPONSE)
						&& b.getServices().containsAll(expert.getServices())
						&& b.getCustomer().getAddress().getCity().equalsIgnoreCase(expert.getAddress().getCity()))
				.findFirst();
		if (booking.isPresent()) {
			BookingDetails booking2 = booking.get();
			bookingService
					.notifyVendors(VendorNotifications.builder().bookingDetails(booking2).experts(expert).build());
		}
	}
}
