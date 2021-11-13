package com.vendor.dao.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.vendor.dao.PackageDao;
import com.vendor.enums.Service;
import com.vendor.exceptions.NotFoundException;
import com.vendor.model.IncludedService;
import com.vendor.model.Package;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class PackageDaoImpl implements PackageDao {

	List<Package> packages;

	public PackageDaoImpl() {
		this.packages = new ArrayList<>();
		this.packages.add(Package.builder().id(1L)
				.includedServices(Arrays.asList(
						IncludedService.builder().id(1L).price(BigInteger.valueOf(999))
								.service(Service.ELECTRICITY_INSTALLATION_SERVICES)
								.descriptions(Arrays.asList("Install AC", "Misc Electricity installation")).build(),
						IncludedService.builder().id(2L).price(BigInteger.valueOf(999))
								.service(Service.ELECTRICITY_REPAIRS_AND_FIXES)
								.descriptions(Arrays.asList("Install AC", "Misc Electricity installation")).build()))
				.build());
		this.packages.add(Package.builder().id(2L)
				.includedServices(Arrays.asList(IncludedService.builder().id(1L).price(BigInteger.valueOf(299))
						.service(Service.ELECTRICITY_INSTALLATION_SERVICES)
						.descriptions(Arrays.asList("Install AC", "Misc Electricity installation")).build()))
				.build());
		this.packages.add(Package.builder().id(3L)
				.includedServices(Arrays.asList(
						IncludedService.builder().id(3L).price(BigInteger.valueOf(999))
								.service(Service.SALON_FOR_WOMEN_HAIR_CUT)
								.descriptions(Arrays.asList("Install AC", "Misc Electricity installation")).build(),
						IncludedService.builder().id(4L).price(BigInteger.valueOf(999))
								.service(Service.SALON_FOR_WOMEN_TAN_REMOVAL)
								.descriptions(Arrays.asList("Install AC", "Misc Electricity installation")).build()))
				.build());
		this.packages.add(Package.builder().id(4L)
				.includedServices(Arrays.asList(
						IncludedService.builder().id(5L).price(BigInteger.valueOf(999))
								.service(Service.YOGA_AMATEUR)
								.descriptions(Arrays.asList("Install AC", "Misc Electricity installation")).build(),
						IncludedService.builder().id(6L).price(BigInteger.valueOf(999))
								.service(Service.YOGA_PROFESSIONAL)
								.descriptions(Arrays.asList("Install AC", "Misc Electricity installation")).build()))
				.build());
	}

	@Override
	public List<Package> findAll() {
		return Objects.nonNull(packages) ? this.packages : Collections.emptyList();
	}

	@Override
	public Package save(Package pack) {
		if (Objects.isNull(pack.getId())) {
			throw new UnsupportedOperationException();
		}
		this.packages.add(pack);
		return pack;
	}

	@Override
	public Package findById(Long identifier) {
		Optional<Package> rating = this.packages.stream().filter(feedbk -> feedbk.getId().equals(identifier)).findAny();
		if (rating.isPresent()) {
			return rating.get();
		}
		throw new NotFoundException("No Rating with id " + identifier + " is found");
	}

	@Override
	public int getSize() {
		return this.packages.size();
	}

	@Override
	public void deleteById(Long identifier) {
		boolean isDeleted = this.packages.removeIf(cust -> cust.getId().equals(identifier));
		if (isDeleted) {
			log.info("Package with id " + identifier + " is deleted successfully");
		} else {
			throw new NotFoundException("No packages with id " + identifier + " is found");
		}
	}
}
