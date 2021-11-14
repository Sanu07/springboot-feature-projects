package com.admin.dao.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.admin.dao.BookingDao;
import com.admin.exceptions.NotFoundException;
import com.admin.model.BookingDetails;

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

}
