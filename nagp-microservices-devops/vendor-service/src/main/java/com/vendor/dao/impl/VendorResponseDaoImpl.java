package com.vendor.dao.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.vendor.dao.VendorResponseDao;
import com.vendor.exceptions.NotFoundException;
import com.vendor.model.VendorResponse;

@Repository
public class VendorResponseDaoImpl implements VendorResponseDao {

	List<VendorResponse> responses;

	public VendorResponseDaoImpl() {
		this.responses = new ArrayList<>();
	}

	@Override
	public List<VendorResponse> findAll() {
		return Objects.nonNull(responses) ? this.responses : Collections.emptyList();
	}

	@Override
	public VendorResponse save(VendorResponse responses) {
		this.responses.add(responses);
		return responses;
	}

	@Override
	public VendorResponse findById(Long identifier) {
		Optional<VendorResponse> rating = this.responses.stream().filter(res -> res.getBookingId().equals(identifier))
				.findAny();
		if (rating.isPresent()) {
			return rating.get();
		}
		throw new NotFoundException("No Rating with id " + identifier + " is found");
	}

	@Override
	public int getSize() {
		return this.responses.size();
	}
}
