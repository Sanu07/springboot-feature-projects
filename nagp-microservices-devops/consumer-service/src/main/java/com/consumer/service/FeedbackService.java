package com.consumer.service;

import java.util.List;

import com.consumer.model.Feedback;

public interface FeedbackService {

	Feedback save(Feedback feedback);
	Feedback findById(Long id);
	List<Feedback> findAll();
}
