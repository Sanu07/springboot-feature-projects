package com.consumer.controller;

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
import com.consumer.service.impl.FeedbackServiceImpl;

@RestController
@RequestMapping("feedbacks")
public class FeedbackController {

	@Autowired
	FeedbackServiceImpl service;
	
	@GetMapping
	public ResponseEntity<List<Feedback>> getAllFeedbacks() {
		return ResponseEntity.ok(service.findAll());
	}
	
	@GetMapping("{id}")
	public ResponseEntity<Feedback> getFeedback(@PathVariable Long id) {
		return ResponseEntity.ok(service.findById(id));
	}
	
	@PostMapping
	public ResponseEntity<Feedback> saveFeedback(@RequestBody Feedback payment) {
		return ResponseEntity.status(HttpStatus.CREATED).body(service.save(payment));
	}
	
}
