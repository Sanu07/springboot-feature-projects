package com.consumer.dao.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.consumer.dao.BookingDao;
import com.consumer.enums.BookingStatus;
import com.consumer.enums.Service;
import com.consumer.exceptions.NotFoundException;
import com.consumer.model.BookingDetails;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class BookingDaoImpl implements BookingDao {

	List<BookingDetails> bookings;

	public BookingDaoImpl() {
		this.bookings = new ArrayList<>();
		this.bookings.add(BookingDetails.builder().id(1L).bookingRaisedAt(LocalDateTime.now()).customerId(1L)
				.feedbackId(1L).paymentId(1L).serviceExpertId(1L)
				.services(
						Arrays.asList(Service.ELECTRICITY_INSTALLATION_SERVICES, Service.ELECTRICITY_REPAIRS_AND_FIXES))
				.status(BookingStatus.COMPLETED).build());
		this.bookings.add(BookingDetails.builder().id(2L).bookingRaisedAt(LocalDateTime.now().plusHours(1)).customerId(2L)
				.feedbackId(2L).paymentId(2L).serviceExpertId(2L)
				.services(
						Arrays.asList(Service.SALON_FOR_WOMEN_HAIR_CUT, Service.SALON_FOR_WOMEN_TAN_REMOVAL))
				.status(BookingStatus.COMPLETED).build());
		this.bookings.add(BookingDetails.builder().id(3L).bookingRaisedAt(LocalDateTime.now().plusHours(2)).customerId(3L)
				.feedbackId(3L).paymentId(3L).serviceExpertId(3L)
				.services(
						Arrays.asList(Service.YOGA_AMATEUR, Service.YOGA_PROFESSIONAL))
				.status(BookingStatus.COMPLETED).build());
	}

	@Override
	public List<BookingDetails> findAll() {
		return Objects.nonNull(bookings) ? this.bookings : Collections.emptyList();
	}

	@Override
	public BookingDetails save(BookingDetails booking) {
		if (Objects.isNull(booking.getId()) || booking.getId() > this.getSize() + 1) {
			booking.setId(this.getSize() + 1L);
			this.bookings.add(booking);
		} else {
			this.bookings.set(booking.getId().intValue(), booking);
		}
		return booking;
	}

	@Override
	public BookingDetails findById(Long identifier) {
		Optional<BookingDetails> booking = this.bookings.stream().filter(bkng -> bkng.getId().equals(identifier))
				.findAny();
		if (booking.isPresent()) {
			return booking.get();
		}
		throw new NotFoundException("No BookingDetails with id " + identifier + " is found");
	}

	@Override
	public int getSize() {
		return this.bookings.size();
	}

	@Override
	public void deleteById(Long identifier) {
		boolean isDeleted = this.bookings.removeIf(bkng -> bkng.getId().equals(identifier));
		if (isDeleted) {
			log.info("Booking with id " + identifier + " is deleted successfully");
		} else {
			throw new NotFoundException("No Booking with id " + identifier + " is found");
		}
	}
}
