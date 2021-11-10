package com.vendor.dao.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.vendor.dao.RatingDao;
import com.vendor.exceptions.NotFoundException;
import com.vendor.model.PackageRating;

public class RatingDaoImpl implements RatingDao {

	List<PackageRating> ratings;

	public RatingDaoImpl() {
		this.ratings = new ArrayList<>();
		this.ratings.add(PackageRating.builder().id(1L).totalRatings(BigInteger.valueOf(123456)).value(4.2f).build());
		this.ratings.add(PackageRating.builder().id(2L).totalRatings(BigInteger.valueOf(25874)).value(3.2f).build());
		this.ratings.add(PackageRating.builder().id(3L).totalRatings(BigInteger.valueOf(6987523)).value(4.4f).build());
		this.ratings.add(PackageRating.builder().id(4L).totalRatings(BigInteger.valueOf(123698)).value(4.6f).build());
	}

	@Override
	public List<PackageRating> findAll() {
		return Objects.nonNull(ratings) ? this.ratings : Collections.emptyList();
	}

	@Override
	public PackageRating save(PackageRating rating) {
		if (Objects.isNull(rating.getId())) {
			throw new UnsupportedOperationException();
		}
		this.ratings.add(rating);
		return rating;
	}

	@Override
	public PackageRating findById(Long identifier) {
		Optional<PackageRating> rating = this.ratings.stream().filter(feedbk -> feedbk.getId().equals(identifier))
				.findAny();
		if (rating.isPresent()) {
			return rating.get();
		}
		throw new NotFoundException("No Rating with id " + identifier + " is found");
	}

	@Override
	public int getSize() {
		return this.ratings.size();
	}
}
