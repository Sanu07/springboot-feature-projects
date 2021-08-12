package com.batch.mongo.processors;

import org.springframework.batch.item.ItemProcessor;

import com.batch.converter.ObjectConverter;
import com.batch.model.Feedback;

public class FeedbackProcessor implements ItemProcessor<Feedback, com.batch.mongo.model.Feedback> {

	@Override
	public com.batch.mongo.model.Feedback process(Feedback feedback) throws Exception {
		com.batch.mongo.model.Feedback mongoFeedback = ObjectConverter.convertFeedbackEntityToDto(feedback);
		mongoFeedback.setOrderId(feedback.getOrder().getId().toString());
		mongoFeedback.setCustomerId(feedback.getCustomer().getId().toString());
		return mongoFeedback;
	}
}
