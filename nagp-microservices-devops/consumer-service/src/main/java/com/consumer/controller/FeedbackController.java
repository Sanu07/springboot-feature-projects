package com.consumer.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.consumer.model.Feedback;
import com.consumer.model.ServiceExpert;
import com.consumer.service.VendorServiceProxy;
import com.consumer.service.impl.FeedbackServiceImpl;

@RestController
@RequestMapping("consumers/feedbacks")
public class FeedbackController {

	@Autowired
	FeedbackServiceImpl service;

	@Autowired
	VendorServiceProxy vendorProxy;

	@GetMapping
	public ResponseEntity<List<Feedback>> getAllFeedbacks() {
		return ResponseEntity.ok(service.findAll());
	}

	@GetMapping("{id}")
	public ResponseEntity<Feedback> getFeedback(@PathVariable Long id) {
		return ResponseEntity.ok(service.findById(id));
	}

	@PostMapping
	public ResponseEntity<Feedback> saveFeedback(@RequestBody Feedback feedback) {
		feedback.setCreatedAt(LocalDateTime.now());
		ResponseEntity<ServiceExpert> responseEntity = vendorProxy.getExpert(feedback.getServiceExpertId());
		ServiceExpert expert = responseEntity.getBody();
		double rating = expert.getRating() == 0 ? feedback.getRatingValue() : expert.getRating();
		expert.setRating((feedback.getRatingValue() + rating) / 2);
		vendorProxy.saveExpert(expert);
		return ResponseEntity.status(HttpStatus.CREATED).body(service.save(feedback));
	}

}
