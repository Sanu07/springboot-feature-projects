package com.consumer.service.impl;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class KafkaProducerServiceImpl {

	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;
	
	public ListenableFuture<SendResult<String, String>> sendEvent(String topic, String key, Object object) throws JsonProcessingException {

		String value = objectMapper.writeValueAsString(object);
		ListenableFuture<SendResult<String, String>> listenableFuture = kafkaTemplate.send(topic, key, value);

		listenableFuture.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
	
			@Override
			public void onFailure(Throwable ex) {
				log.error("Error in publishing message {} to kafka topic {}", value, topic);
			}
			
			@Override
			public void onSuccess(SendResult<String, String> result) {
				log.info("=============================================================================================");
				log.info("Your order {} has been received. We will notify you once a service expert is assigned to you.", value);
				log.info("=============================================================================================");
			}
		});
		return listenableFuture;
	}
}
