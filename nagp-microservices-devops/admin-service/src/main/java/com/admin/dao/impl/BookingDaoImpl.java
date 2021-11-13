package com.admin.dao.impl;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.admin.dao.BookingDao;
import com.admin.enums.City;
import com.admin.enums.Country;
import com.admin.enums.PaymentMode;
import com.admin.enums.Service;
import com.admin.enums.State;
import com.admin.exceptions.NotFoundException;
import com.admin.model.Address;
import com.admin.model.BookingDetails;
import com.admin.model.Customer;
import com.admin.model.Payment;
import com.admin.model.ServiceExpert;

public class BookingDaoImpl implements BookingDao {

	List<BookingDetails> bookings;

	public BookingDaoImpl() {
		this.bookings = new ArrayList<>();
		this.bookings.add(BookingDetails.builder().id(1L).customer(Customer.builder().id(1L)
				.address(Address.builder().id(1L).addressLine("11/1 street lane").pinCode(25896L)
						.country(Country.INDIA.name()).state(State.HARYANA.name()).city(City.GURUGRAM.name()).build())
				.name("Victoria").phone("3698745210").build())
				.paymnent(Payment.builder().id(1L).amountPaid(BigInteger.valueOf(299)).dueAmount(BigInteger.ZERO)
						.mode(PaymentMode.ONLINE).paidAt(LocalDateTime.now()).build())
				.serviceExpert(ServiceExpert.builder().id(2L)
						.address(Address.builder().id(2L).addressLine("22/2 street lane").pinCode(25896L)
								.country(Country.INDIA.name()).state(State.WEST_BENGAL.name()).city(City.KOLKATA.name())
								.build())
						.name("Victor").services(Arrays.asList(Service.ELECTRICITY_REPAIRS_AND_FIXES)).build())
				.bookingAcceptedAt(LocalDateTime.now().plusMinutes(5)).bookingRaisedAt(LocalDateTime.now()).build());
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
