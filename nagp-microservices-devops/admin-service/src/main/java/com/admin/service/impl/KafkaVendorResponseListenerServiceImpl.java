package com.admin.service.impl;

import java.time.LocalDateTime;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.AcknowledgingMessageListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

import com.admin.constants.AppConstants;
import com.admin.dao.impl.BookingDaoImpl;
import com.admin.model.BookingDetails;
import com.admin.model.VendorNotifications;
import com.admin.model.VendorResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class KafkaVendorResponseListenerServiceImpl implements AcknowledgingMessageListener<String, String> {

	@Autowired
	ObjectMapper mapper;

	@Autowired
	BookingServiceImpl bookingService;

	@Autowired
	BookingDaoImpl repo;

	@KafkaListener(topics = { AppConstants.VENDOR_RESPONSE_TOPIC })
	@Override
	public void onMessage(ConsumerRecord<String, String> consumerRecord, Acknowledgment acknowledgment) {
		log.info("ConsumerRecord : {} ", consumerRecord);
		try {
			VendorResponse vendorResponse = mapper.readValue(consumerRecord.value(), VendorResponse.class);
			if (!vendorResponse.isAccepted()) {
				log.info("expert {} did not accept booking details with bookingId {}", vendorResponse.getExpert(),
						vendorResponse.getBookingId());
				repo.updateVendorsNotifiedMap(vendorResponse);
				bookingService.notifyVendors(VendorNotifications.builder()
						.bookingDetails(bookingService.findById(vendorResponse.getBookingId()))
						.experts(vendorResponse.getExpert()).build());
				return;
			}
			BookingDetails bookingDetails = bookingService.findById(vendorResponse.getBookingId());
			bookingDetails.setServiceExpert(vendorResponse.getExpert());
			bookingDetails.setBookingAcceptedAt(LocalDateTime.now());
			bookingService.save(bookingDetails);
			bookingService.notifyCustomers(vendorResponse);
		} catch (JsonProcessingException e) {
			log.error("Error while reading value ", e);
		}
		acknowledgment.acknowledge();
	}
}
