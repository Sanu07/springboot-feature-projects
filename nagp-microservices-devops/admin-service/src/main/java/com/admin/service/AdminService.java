package com.admin.service;

import java.util.List;

import com.admin.model.Admin;

public interface AdminService {

	Admin save(Admin feedback);
	Admin findById(Long id);
	List<Admin> findAll();
	public void deleteById(Long id);
}