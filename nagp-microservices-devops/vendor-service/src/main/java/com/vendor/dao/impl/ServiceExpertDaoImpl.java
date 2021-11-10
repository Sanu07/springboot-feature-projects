package com.vendor.dao.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.vendor.dao.ServiceExpertDao;
import com.vendor.enums.City;
import com.vendor.enums.Country;
import com.vendor.enums.Service;
import com.vendor.enums.State;
import com.vendor.exceptions.NotFoundException;
import com.vendor.model.Address;
import com.vendor.model.ServiceExpert;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ServiceExpertDaoImpl implements ServiceExpertDao {

	private List<ServiceExpert> serviceExperts;

	public ServiceExpertDaoImpl() {
		this.serviceExperts = new ArrayList<>();
		this.serviceExperts.add(ServiceExpert.builder().id(1L)
				.address(Address.builder().id(1L).addressLine("11/1 street lane").pinCode(25896L)
						.country(Country.INDIA.name()).state(State.HARYANA.name()).city(City.GURUGRAM.name()).build())
				.name("Victoria").services(Arrays.asList(Service.ELECTRICITY_INSTALLATION_SERVICES, Service.ELECTRICITY_REPAIRS_AND_FIXES)).build());
		this.serviceExperts.add(ServiceExpert.builder().id(2L)
				.address(Address.builder().id(2L).addressLine("22/2 street lane").pinCode(25896L)
						.country(Country.INDIA.name()).state(State.WEST_BENGAL.name()).city(City.KOLKATA.name())
						.build())
				.name("Victor").services(Arrays.asList(Service.ELECTRICITY_REPAIRS_AND_FIXES)).build());
		this.serviceExperts.add(ServiceExpert.builder().id(3L)
				.address(Address.builder().id(3L).addressLine("33/3 street lane").pinCode(25896L)
						.country(Country.INDIA.name()).state(State.HARYANA.name()).city(City.GURUGRAM.name()).build())
				.name("Vanessa").services(Arrays.asList(Service.SALON_FOR_WOMEN_HAIR_CUT, Service.SALON_FOR_WOMEN_TAN_REMOVAL)).build());
		this.serviceExperts.add(ServiceExpert.builder().id(4L)
				.address(Address.builder().id(4L).addressLine("44/4 street lane").pinCode(25896L)
						.country(Country.INDIA.name()).state(State.WEST_BENGAL.name()).city(City.KOLKATA.name())
						.build())
				.name("Valentina").services(Arrays.asList(Service.YOGA_AMATEUR, Service.YOGA_PROFESSIONAL)).build());
	}

	@Override
	public List<ServiceExpert> findAll() {
		return Objects.nonNull(serviceExperts) ? this.serviceExperts : Collections.emptyList();
	}

	@Override
	public ServiceExpert save(ServiceExpert serviceExpert) {
		if (Objects.isNull(serviceExpert.getId())) {
			throw new UnsupportedOperationException();
		}
		this.serviceExperts.add(serviceExpert);
		return serviceExpert;
	}

	@Override
	public ServiceExpert findById(Long identifier) {
		Optional<ServiceExpert> serviceExpert = this.serviceExperts.stream().filter(cust -> cust.getId().equals(identifier)).findAny();
		if (serviceExpert.isPresent()) {
			return serviceExpert.get();
		}
		throw new NotFoundException("No ServiceExpert with id " + identifier + " is found");
	}

	@Override
	public void deleteById(Long identifier) {
		boolean isDeleted = this.serviceExperts.removeIf(cust -> cust.getId().equals(identifier));
		if (isDeleted) {
			log.info("ServiceExpert with id " + identifier + " is deleted successfully");
		} else {
			throw new NotFoundException("No ServiceExpert with id " + identifier + " is found");
		}
	}

	@Override
	public int getSize() {
		return this.serviceExperts.size();
	}

}
