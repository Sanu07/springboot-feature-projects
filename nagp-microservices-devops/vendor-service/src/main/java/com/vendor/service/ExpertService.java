package com.vendor.service;

import java.util.List;

import com.vendor.model.ServiceExpert;

public interface ExpertService {

	ServiceExpert save(ServiceExpert expert);
	ServiceExpert findById(Long id);
	List<ServiceExpert> findAll();
	void deleteById(Long id);
}
