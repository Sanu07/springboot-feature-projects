package com.consumer.dao.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.consumer.dao.ConsumerDao;
import com.consumer.enums.City;
import com.consumer.enums.Country;
import com.consumer.enums.State;
import com.consumer.exceptions.NotFoundException;
import com.consumer.model.Address;
import com.consumer.model.Consumer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class ConsumerDaoImpl implements ConsumerDao {

	private List<Consumer> customers;

	public ConsumerDaoImpl() {
		this.customers = new ArrayList<>();
		this.customers.add(Consumer.builder().id(1L)
				.address(Address.builder().id(1L).addressLine("11/1 street lane").pinCode(25896L)
						.country(Country.INDIA.name()).state(State.HARYANA.name()).city(City.GURUGRAM.name()).build())
				.name("Victoria").phone("3698745210").build());
		this.customers.add(Consumer.builder().id(2L)
				.address(Address.builder().id(2L).addressLine("22/2 street lane").pinCode(25896L)
						.country(Country.INDIA.name()).state(State.WEST_BENGAL.name()).city(City.KOLKATA.name())
						.build())
				.name("Victor").phone("2589631470").build());
		this.customers.add(Consumer.builder().id(3L)
				.address(Address.builder().id(3L).addressLine("33/3 street lane").pinCode(25896L)
						.country(Country.INDIA.name()).state(State.HARYANA.name()).city(City.GURUGRAM.name()).build())
				.name("Vanessa").phone("6985471230").build());
		this.customers.add(Consumer.builder().id(4L)
				.address(Address.builder().id(4L).addressLine("44/4 street lane").pinCode(25896L)
						.country(Country.INDIA.name()).state(State.WEST_BENGAL.name()).city(City.KOLKATA.name())
						.build())
				.name("Valentina").phone("2103654789").build());
	}

	@Override
	public List<Consumer> findAll() {
		return Objects.nonNull(customers) ? this.customers : Collections.emptyList();
	}

	@Override
	public Consumer save(Consumer customer) {
		if (Objects.isNull(customer.getId()) || customer.getId() > this.getSize()) {
			customer.setId(this.getSize() + 1L);
			this.customers.add(customer);
		} else {
			this.customers.set(customer.getId().intValue() - 1, customer);
		}
		return customer;
	}

	@Override
	public Consumer findById(Long identifier) {
		Optional<Consumer> customer = this.customers.stream().filter(cust -> cust.getId().equals(identifier)).findAny();
		if (customer.isPresent()) {
			return customer.get();
		}
		throw new NotFoundException("No Consumer with id " + identifier + " is found");
	}

	@Override
	public void deleteById(Long identifier) {
		boolean isDeleted = this.customers.removeIf(cust -> cust.getId().equals(identifier));
		if (isDeleted) {
			log.info("Customer with id " + identifier + " is deleted successfully");
		} else {
			throw new NotFoundException("No customer with id " + identifier + " is found");
		}
	}

	@Override
	public int getSize() {
		return this.customers.size();
	}

}
