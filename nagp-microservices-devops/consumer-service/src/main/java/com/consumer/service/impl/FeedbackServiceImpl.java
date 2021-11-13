package com.consumer.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.consumer.dao.impl.FeedbackDaoImpl;
import com.consumer.model.Feedback;
import com.consumer.service.FeedbackService;

@Service
public class FeedbackServiceImpl implements FeedbackService {

	@Autowired
	FeedbackDaoImpl repo;
	
	@Override
	public Feedback save(Feedback feedback) {
		return repo.save(feedback);
	}

	@Override
	public Feedback findById(Long id) {
		return repo.findById(id);
	}

	@Override
	public List<Feedback> findAll() {
		return repo.findAll();
	}

}
