package com.admin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.admin.dao.impl.AdminDaoImpl;
import com.admin.model.Admin;
import com.admin.service.AdminService;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	AdminDaoImpl repo;
	
	@Override
	public Admin save(Admin admin) {
		return repo.save(admin);
	}

	@Override
	public Admin findById(Long id) {
		return repo.findById(id);
	}

	@Override
	public List<Admin> findAll() {
		return repo.findAll();
	}

	@Override
	public void deleteById(Long id) {
		repo.deleteById(id);
	}

	public int getTotalAdmins() {
		return repo.getSize();
	}
}
