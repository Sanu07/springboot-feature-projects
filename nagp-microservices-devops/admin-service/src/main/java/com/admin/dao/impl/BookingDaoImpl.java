package com.admin.dao.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.admin.dao.BookingDao;
import com.admin.exceptions.NotFoundException;
import com.admin.model.BookingDetails;
import com.admin.model.ServiceExpert;
import com.admin.model.VendorResponse;

@Repository
public class BookingDaoImpl implements BookingDao {

	List<BookingDetails> bookings;
	
	Map<Long, List<ServiceExpert>> vendorMap;

	public BookingDaoImpl() {
		this.bookings = new ArrayList<>();
	}

	@Override
	public List<BookingDetails> findAll() {
		return Objects.nonNull(bookings) ? this.bookings : Collections.emptyList();
	}

	@Override
	public BookingDetails save(BookingDetails booking) {
		if (Objects.isNull(booking.getId())) {
			throw new UnsupportedOperationException();
		}
		this.bookings.add(booking);
		return booking;
	}

	@Override
	public BookingDetails findById(Long identifier) {
		Optional<BookingDetails> booking = this.bookings.stream().filter(bkng -> bkng.getId().equals(identifier))
				.findAny();
		if (booking.isPresent()) {
			return booking.get();
		}
		throw new NotFoundException("No Booking Details with id " + identifier + " is found");
	}

	@Override
	public int getSize() {
		return this.bookings.size();
	}
	
	public ServiceExpert updateVendorsNotifiedMap(VendorResponse response) {
		ServiceExpert expert = null;
		if (response.isAccepted()) {
			vendorMap.remove(response.getBookingId());
		} else {
			List<ServiceExpert> list = vendorMap.get(response.getBookingId());
			list.remove(response.getExpert());
			if (!list.isEmpty()) {
				expert = list.get(0);
			}
			vendorMap.put(response.getBookingId(), list);
		}
		return expert;
	}
	
	public void saveToVendorsMap(List<ServiceExpert> experts, Long bookingId) {
		if (Objects.isNull(vendorMap)) {
			vendorMap = new HashMap<>();
		}
		vendorMap.put(bookingId, experts);
	}

}
