package com.admin.service.impl;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.AcknowledgingMessageListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

import com.admin.constants.AppConstants;
import com.admin.model.BookingDetails;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class KafkaBookingListenerServiceImpl implements AcknowledgingMessageListener<String, String> {

	@Autowired
	ObjectMapper mapper;
	
	@Autowired
	BookingServiceImpl bookingService;
	
	@KafkaListener(topics = { AppConstants.CONSUMER_SERVICE_BOOKING_TOPIC })
	@Override
	public void onMessage(ConsumerRecord<String, String> consumerRecord, Acknowledgment acknowledgment) {
		log.info("ConsumerRecord : {} ", consumerRecord);
		try {
			BookingDetails bookingDetails = mapper.readValue(consumerRecord.value(), BookingDetails.class);
			bookingService.save(bookingDetails);
			bookingService.notifyVendors(bookingDetails);
		} catch (JsonProcessingException e) {
			log.error("Error while reading value ", e);
		}
		acknowledgment.acknowledge();
	}
	
}
