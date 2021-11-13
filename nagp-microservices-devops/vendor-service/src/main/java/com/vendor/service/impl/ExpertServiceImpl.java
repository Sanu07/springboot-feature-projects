package com.vendor.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vendor.dao.impl.ExpertDaoImpl;
import com.vendor.model.ServiceExpert;
import com.vendor.service.ExpertService;

@Service
public class ExpertServiceImpl implements ExpertService {

	@Autowired
	ExpertDaoImpl repo;
	
	@Override
	public ServiceExpert save(ServiceExpert expert) {
		return repo.save(expert);
	}

	@Override
	public ServiceExpert findById(Long id) {
		return repo.findById(id);
	}

	@Override
	public List<ServiceExpert> findAll() {
		return repo.findAll();
	}

	@Override
	public void deleteById(Long id) {
		repo.deleteById(id);
	}

}
