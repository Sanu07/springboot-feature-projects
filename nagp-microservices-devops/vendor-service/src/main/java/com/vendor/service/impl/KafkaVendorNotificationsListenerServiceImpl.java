package com.vendor.service.impl;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.AcknowledgingMessageListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vendor.constants.AppConstants;
import com.vendor.model.VendorNotifications;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class KafkaVendorNotificationsListenerServiceImpl implements AcknowledgingMessageListener<String, String> {

	@Autowired
	ObjectMapper mapper;
	
	
	@KafkaListener(topics = { AppConstants.VENDOR_NOTIFICATIONS_TOPIC })
	@Override
	public void onMessage(ConsumerRecord<String, String> consumerRecord, Acknowledgment acknowledgment) {
		log.info("ConsumerRecord : {} ", consumerRecord);
		acknowledgment.acknowledge();
		try {
			VendorNotifications vendorNotifications = mapper.readValue(consumerRecord.value(), VendorNotifications.class);
			log.info("*****************************Sending Notifications to vendors*************************************");
			log.info("Service Expert details who has been notified {}", vendorNotifications.getExperts());
			log.info("**************************************************************************************************");
		} catch (JsonProcessingException e) {
			log.error("Error while reading value ", e);
		}
	}
}
