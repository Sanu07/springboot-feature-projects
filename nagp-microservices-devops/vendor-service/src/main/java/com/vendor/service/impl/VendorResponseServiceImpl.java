package com.vendor.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vendor.dao.impl.VendorResponseDaoImpl;
import com.vendor.model.VendorResponse;

@Service
public class VendorResponseServiceImpl {

	@Autowired
	VendorResponseDaoImpl repo;
	
	public List<VendorResponse> findAll() {
		return repo.findAll();
	}
	
	public VendorResponse save(VendorResponse vendorResponse) {
		return repo.save(vendorResponse);
	}
	
	public VendorResponse findByBookingId(Long id) {
		return repo.findById(id);
	}
}
