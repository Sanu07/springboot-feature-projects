package com.consumer.dao.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.consumer.dao.FeedbackDao;
import com.consumer.exceptions.NotFoundException;
import com.consumer.model.Feedback;

@Repository
public class FeedbackDaoImpl implements FeedbackDao {

	List<Feedback> feedbacks;

	public FeedbackDaoImpl() {
		this.feedbacks = new ArrayList<>();
	}

	@Override
	public List<Feedback> findAll() {
		return Objects.nonNull(feedbacks) ? this.feedbacks : Collections.emptyList();
	}

	@Override
	public Feedback save(Feedback feedback) {
		if (Objects.isNull(feedback.getId()) || feedback.getId() > this.getSize()) {
			feedback.setId(this.getSize() + 1L);
			this.feedbacks.add(feedback);
		} else {
			this.feedbacks.set(feedback.getId().intValue(), feedback);
		}
		return feedback;
	}

	@Override
	public Feedback findById(Long identifier) {
		Optional<Feedback> feedback = this.feedbacks.stream().filter(feedbk -> feedbk.getId().equals(identifier))
				.findAny();
		if (feedback.isPresent()) {
			return feedback.get();
		}
		throw new NotFoundException("No Feedback with id " + identifier + " is found");
	}

	@Override
	public int getSize() {
		return this.feedbacks.size();
	}
}
