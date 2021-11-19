package com.admin.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.admin.model.BookingDetails;
import com.admin.model.Customer;
import com.admin.model.Feedback;
import com.admin.model.Payment;
import com.admin.model.ServiceExpert;
import com.admin.proxy.service.ConsumerServiceProxy;
import com.admin.proxy.service.VendorServiceProxy;
import com.admin.service.impl.BookingServiceImpl;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("admin")
@Slf4j
public class AdminDashboardController {

	@Autowired
	ConsumerServiceProxy consumerProxy;

	@Autowired
	VendorServiceProxy vendorProxy;

	@Autowired
	BookingServiceImpl bookingService;

	@GetMapping("acustomers")
	public ResponseEntity<List<Customer>> getAllCustomers() {
		return ResponseEntity.ok(consumerProxy.findAll());
	}

	@GetMapping("customers/{id}")
	public Customer getConsumer(@PathVariable Long id) {
		return consumerProxy.findById(id);
	}

	@GetMapping("bookingsHistory")
	public ResponseEntity<List<BookingDetails>> getAllBookings(@RequestParam String month) {
		List<BookingDetails> bookingDetails = bookingService.findAll();
		if (Objects.nonNull(month)) {
			long specifiedMonth = Long.parseLong(month);
			if (specifiedMonth < 7 && specifiedMonth > 0) {
				LocalDateTime now = LocalDateTime.now();
				LocalDateTime specifiedDuration = now.minusMonths(specifiedMonth);
				return ResponseEntity.ok(bookingDetails.stream().filter(bd -> {
					return bd.getBookingRaisedAt().isAfter(specifiedDuration);
				}).collect(Collectors.toUnmodifiableList()));
			} else {
				throw new IllegalArgumentException("Invalid month parameter");
			}
		}
		return ResponseEntity.ok(bookingDetails);
	}

	@GetMapping("vendors")
	public ResponseEntity<List<ServiceExpert>> getAllVendors() {
		return ResponseEntity.ok(vendorProxy.findAll());
	}

	@GetMapping("paymentsHistory")
	public ResponseEntity<List<Payment>> getPaymentsHistory(@RequestParam String month) {
		ResponseEntity<List<Payment>> payments = consumerProxy.getAllPayments();
		if (Objects.isNull(payments) || Objects.isNull(payments.getBody())) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ArrayList<>());
		}
		if (Objects.nonNull(month)) {
			long specifiedMonth = Long.parseLong(month);
			if (specifiedMonth < 7 && specifiedMonth > 0) {
				LocalDateTime now = LocalDateTime.now();
				LocalDateTime specifiedDuration = now.minusMonths(specifiedMonth);
				return ResponseEntity.ok(payments.getBody().stream().filter(pmnt -> {
					return pmnt.getPaidAt().isAfter(specifiedDuration);
				}).collect(Collectors.toUnmodifiableList()));
			} else {
				throw new IllegalArgumentException("Invalid month parameter");
			}
		}
		return ResponseEntity.ok(payments.getBody());
	}

	@GetMapping("topaRatedExperts")
	public ResponseEntity<List<ServiceExpert>> getTopRatedServiceExperts(@RequestParam String top) {
		ResponseEntity<List<Feedback>> feedbacks = consumerProxy.getAllFeedbacks();
		if (Objects.isNull(feedbacks) || Objects.isNull(feedbacks.getBody())) {
			return ResponseEntity.ok().body(new ArrayList<>());
		}
		List<Feedback> feedbackList = feedbacks.getBody();
		int topLimit = feedbackList.size();
		try {
			topLimit = Integer.parseInt(top);
		} catch (Exception e) {
			log.error("Invalid top parameter", e);
			throw new IllegalArgumentException("Invalid top parameter");
		}
		Map<Long, Double> expertMap = feedbackList.stream().collect(Collectors.groupingBy(Feedback::getServiceExpertId,
				Collectors.averagingDouble(Feedback::getRatingValue)));
		Map<Long, Double> sortedByRating = expertMap.entrySet().stream().sorted(Map.Entry.comparingByValue())
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
		return ResponseEntity.ok(sortedByRating.entrySet().stream().limit(topLimit).map(se -> {
			ServiceExpert expert = vendorProxy.findById(se.getKey());
			expert.setRating(se.getValue());
			return expert;
		}).collect(Collectors.toList()));
	}
}
