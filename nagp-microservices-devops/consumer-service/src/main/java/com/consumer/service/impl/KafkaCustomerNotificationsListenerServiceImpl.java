package com.consumer.service.impl;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.AcknowledgingMessageListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

import com.consumer.constants.AppConstants;
import com.consumer.model.VendorResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class KafkaCustomerNotificationsListenerServiceImpl implements AcknowledgingMessageListener<String, String> {

	@Autowired
	ObjectMapper mapper;
	
	
	@KafkaListener(topics = { AppConstants.CUSTOMER_NOTIFICATIONS_TOPIC })
	@Override
	public void onMessage(ConsumerRecord<String, String> consumerRecord, Acknowledgment acknowledgment) {
		log.info("ConsumerRecord : {} ", consumerRecord);
		try {
			VendorResponse vendorResponse = mapper.readValue(consumerRecord.value(), VendorResponse.class);
			log.info("*********Notifying Customers with the expert assigned**********");
			log.info("{}", vendorResponse.getExpert());
			log.info("***************************************************************");
		} catch (JsonProcessingException e) {
			log.error("Error while reading value ", e);
		}
		acknowledgment.acknowledge();
	}
}
