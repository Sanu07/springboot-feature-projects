package com.admin.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.admin.constants.AppConstants;
import com.admin.dao.impl.BookingDaoImpl;
import com.admin.model.BookingDetails;
import com.admin.model.ServiceExpert;
import com.admin.model.VendorNotifications;
import com.admin.model.VendorResponse;
import com.admin.service.BookingService;
import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BookingServiceImpl implements BookingService {

	@Autowired
	BookingDaoImpl repo;

	@Autowired
	VendorNotificationServiceImpl vendorNotificationService;

	@Autowired
	KafkaProducerServiceImpl kafkaService;

	List<ServiceExpert> serviceExpertsList;
	
	@Override
	public BookingDetails save(BookingDetails booking) {
		return repo.save(booking);
	}

	@Override
	public BookingDetails findById(Long id) {
		return repo.findById(id);
	}

	@Override
	public List<BookingDetails> findAll() {
		return repo.findAll();
	}

	public void notifyVendors(BookingDetails bookingDetails) {
		List<ServiceExpert> experts = vendorNotificationService.findExpertsByProfession(bookingDetails.getServices());
		List<ServiceExpert> expertsInSameArea = experts.stream()
				.filter(expert -> expert.getAddress().getCity()
						.equalsIgnoreCase(bookingDetails.getCustomer().getAddress().getCity()))
				.sorted((e1, e2) -> {
					return Long.compare(Math.abs(e1.getAddress().getPinCode() - bookingDetails.getCustomer().getAddress().getPinCode()),
							Math.abs(e2.getAddress().getPinCode() - bookingDetails.getCustomer().getAddress().getPinCode()));
				})
				.collect(Collectors.toList());
		if (expertsInSameArea.isEmpty()) {
			expertsInSameArea = experts;
		}
		repo.saveToVendorsMap(expertsInSameArea, bookingDetails.getId());
		VendorNotifications vendorNotifications = VendorNotifications.builder().bookingDetails(bookingDetails)
				.experts(expertsInSameArea.get(0)).build();
		notifyVendors(vendorNotifications);
	}
	
	public void notifyVendors(VendorNotifications vendorNotifications) {
		try {
			kafkaService.sendEvent(AppConstants.VENDOR_NOTIFICATIONS_TOPIC, vendorNotifications.getBookingDetails().getId().toString(),
					vendorNotifications);
			log.info("event generated for vendor {}", vendorNotifications.getExperts());
		} catch (JsonProcessingException e) {
			log.error("Error in notifying vendors ", e);
		}
	}

	public void notifyCustomers(VendorResponse vendorResponse) {
		try {
			kafkaService.sendEvent(AppConstants.CONSUMER_NOTIFICATIONS_TOPIC, vendorResponse.getBookingId().toString(),
					vendorResponse);
			log.info("Customer has been assigned an expert {} for booking Id {}", vendorResponse.getExpert(),
					vendorResponse.getBookingId());
		} catch (JsonProcessingException e) {
			log.error("Error in notifying customer ", e);
		}
	}

}
