package com.vendor.dao.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.vendor.dao.BookingDao;
import com.vendor.exceptions.NotFoundException;
import com.vendor.model.BookingDetails;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class BookingDaoImpl implements BookingDao {

	List<BookingDetails> bookings;

	public BookingDaoImpl() {
		this.bookings = new ArrayList<>();
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
