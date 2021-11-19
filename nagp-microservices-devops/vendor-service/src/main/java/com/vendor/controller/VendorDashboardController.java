package com.vendor.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.vendor.constants.AppConstants;
import com.vendor.model.BookingDetails;
import com.vendor.model.Feedback;
import com.vendor.model.ServiceExpert;
import com.vendor.model.VendorResponse;
import com.vendor.service.ConsumerServiceProxy;
import com.vendor.service.impl.BookingServiceImpl;
import com.vendor.service.impl.ExpertServiceImpl;
import com.vendor.service.impl.KafkaProducerServiceImpl;
import com.vendor.service.impl.VendorResponseServiceImpl;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class VendorDashboardController {

	@Autowired
	ExpertServiceImpl expertService;

	@Autowired
	KafkaProducerServiceImpl kafkaService;

	@Autowired
	VendorResponseServiceImpl vendorResponseService;

	@Autowired
	ConsumerServiceProxy consumerProxy;

	@Autowired
	BookingServiceImpl bookingService;

	@GetMapping("vendors/bookingResponse/vendorID/{id}")
	public void acceptBookingRequest(@PathVariable Long id, @RequestParam String accept, @RequestParam Long bookingId) {
		boolean isAccepted = Boolean.parseBoolean(accept);
		ServiceExpert expert = null;
		expert = expertService.findById(id);
		VendorResponse vendorResponse = VendorResponse.builder().bookingId(bookingId).expert(expert)
				.requestAcceptedAt(LocalDateTime.now()).isAccepted(isAccepted).build();
		vendorResponseService.save(vendorResponse);
		try {
			kafkaService.sendEvent(AppConstants.VENDOR_RESPONSE_TOPIC, bookingId.toString(), vendorResponse);
		} catch (JsonProcessingException e) {
			log.error("Error in sending vendor response", e);
		}
	}

	@GetMapping("vendors/feedbacks/{expertId}")
	public ResponseEntity<List<Feedback>> getAllFeedbacks(@PathVariable Long expertId) {
		List<VendorResponse> vendorResponses = vendorResponseService.findAll();
		List<Long> bookingIds = vendorResponses.stream().filter(vRes -> vRes.getExpert().getId().equals(expertId))
				.map(VendorResponse::getBookingId).collect(Collectors.toList());
		List<Feedback> feedbacks = consumerProxy.findAllFeedbacks();
		List<Feedback> res = feedbacks.stream().filter(fd -> bookingIds.contains(fd.getId()))
				.collect(Collectors.toList());
		return ResponseEntity.ok(res);
	}

	@GetMapping("vendors/bookingHistory/{vendorId}")
	public ResponseEntity<List<BookingDetails>> getAllBookings(@PathVariable Long vendorId) {
		List<BookingDetails> res = bookingService.findAll().stream()
				.filter(bk -> bk.getExpert().getId().equals(vendorId)).collect(Collectors.toList());
		return ResponseEntity.ok(res);
	}
}
