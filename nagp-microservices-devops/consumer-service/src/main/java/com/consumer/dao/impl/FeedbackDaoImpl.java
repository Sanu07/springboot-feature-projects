package com.consumer.dao.impl;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
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
		this.feedbacks.add(Feedback.builder().createdAt(LocalDateTime.now()).description("The service was good").id(1L)
				.ratingValue(4).build());
		this.feedbacks.add(Feedback.builder().createdAt(LocalDateTime.now().plus(1L, ChronoUnit.HOURS))
				.description("I liked the service but expected more").id(2L).ratingValue(5).build());
		this.feedbacks.add(Feedback.builder().createdAt(LocalDateTime.now()).description("It was awesome").id(3L)
				.ratingValue(4).build());
	}

	@Override
	public List<Feedback> findAll() {
		return Objects.nonNull(feedbacks) ? this.feedbacks : Collections.emptyList();
	}

	@Override
	public Feedback save(Feedback feedback) {
		if (Objects.isNull(feedback.getId())) {
			throw new UnsupportedOperationException();
		}
		this.feedbacks.add(feedback);
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
