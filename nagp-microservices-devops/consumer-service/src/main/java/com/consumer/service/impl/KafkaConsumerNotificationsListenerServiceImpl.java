package com.consumer.service.impl;

import java.util.Objects;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.AcknowledgingMessageListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

import com.consumer.constants.AppConstants;
import com.consumer.enums.BookingStatus;
import com.consumer.model.BookingDetails;
import com.consumer.model.VendorResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class KafkaConsumerNotificationsListenerServiceImpl implements AcknowledgingMessageListener<String, String> {

	@Autowired
	ObjectMapper mapper;
	
	@Autowired
	BookingServiceImpl bookingService;
	
	@KafkaListener(topics = { AppConstants.CONSUMER_NOTIFICATIONS_TOPIC })
	@Override
	public void onMessage(ConsumerRecord<String, String> consumerRecord, Acknowledgment acknowledgment) {
		log.info("ConsumerRecord : {} ", consumerRecord);
		acknowledgment.acknowledge();
		try {
			VendorResponse vendorResponse = mapper.readValue(consumerRecord.value(), VendorResponse.class);
			if (Objects.isNull(vendorResponse.getExpert())) {
				log.info("We apologize!! We don't have the required vendors as of now to serve your request.");
				BookingDetails bookingDetails = bookingService.findById(vendorResponse.getBookingId());
				bookingDetails.setStatus(BookingStatus.NO_RESPONSE);
				bookingService.save(bookingDetails);
				return;
			}
			log.info("*********Notifying Customers with the expert assigned**********");
			log.info("{}", vendorResponse.getExpert());
			BookingDetails bookingDetails = bookingService.findById(vendorResponse.getBookingId());
			bookingDetails.setStatus(BookingStatus.COMPLETED);
			bookingDetails.setServiceExpert(vendorResponse.getExpert());
			bookingService.save(bookingDetails);
			log.info("***************************************************************");
		} catch (JsonProcessingException e) {
			log.error("Error while reading value ", e);
		}
	}
}
