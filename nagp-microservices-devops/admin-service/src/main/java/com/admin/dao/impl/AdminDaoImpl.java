package com.admin.dao.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.admin.dao.AdminDao;
import com.admin.exceptions.NotFoundException;
import com.admin.model.Admin;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AdminDaoImpl implements AdminDao {

	private List<Admin> admins;

	public AdminDaoImpl() {
		this.admins = new ArrayList<>();
		this.admins.add(Admin.builder().id(1L).name("Allen").phone("3698521470").build());
		this.admins.add(Admin.builder().id(1L).name("Borris").phone("3698521470").build());
		this.admins.add(Admin.builder().id(1L).name("Chandler").phone("3698521470").build());
		this.admins.add(Admin.builder().id(1L).name("Donald").phone("3698521470").build());
	}

	@Override
	public List<Admin> findAll() {
		return Objects.nonNull(admins) ? this.admins : Collections.emptyList();
	}

	@Override
	public Admin save(Admin admin) {
		if (Objects.isNull(admin.getId())) {
			throw new UnsupportedOperationException();
		}
		this.admins.add(admin);
		return admin;
	}

	@Override
	public Admin findById(Long identifier) {
		Optional<Admin> admin = this.admins.stream().filter(cust -> cust.getId().equals(identifier)).findAny();
		if (admin.isPresent()) {
			return admin.get();
		}
		throw new NotFoundException("No admin with id " + identifier + " is found");
	}

	@Override
	public void deleteById(Long identifier) {
		boolean isDeleted = this.admins.removeIf(cust -> cust.getId().equals(identifier));
		if (isDeleted) {
			log.info("Admin with id " + identifier + " is deleted successfully");
		} else {
			throw new NotFoundException("No admin with id " + identifier + " is found");
		}
	}

	@Override
	public int getSize() {
		return this.admins.size();
	}

}
