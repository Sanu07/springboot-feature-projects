package com.vendor.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.vendor.constants.AppConstants;
import com.vendor.model.BookingDetails;
import com.vendor.model.ServiceExpert;
import com.vendor.model.VendorResponse;
import com.vendor.service.impl.ExpertServiceImpl;
import com.vendor.service.impl.KafkaProducerServiceImpl;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class VendorServiceController {

	@Autowired
	ExpertServiceImpl expertService;

	@Autowired
	KafkaProducerServiceImpl kafkaService;
	
	@GetMapping("vendors/bookingResponse/vendorID/{id}")
	public void acceptBookingRequest(@PathVariable Long id, @RequestParam String accept, @RequestParam Long bookingId) {
		boolean isAccepted = Boolean.parseBoolean(accept);
		ServiceExpert expert = null;
		expert = expertService.findById(id);
		VendorResponse vendorResponse = VendorResponse.builder().bookingId(bookingId).expert(expert)
				.requestAcceptedAt(LocalDateTime.now()).isAccepted(isAccepted).build();
		try {
			kafkaService.sendEvent(AppConstants.VENDOR_RESPONSE_TOPIC, bookingId.toString(), vendorResponse);
		} catch (JsonProcessingException e) {
			log.error("Error in sending vendor response", e);
		}
	}

}
