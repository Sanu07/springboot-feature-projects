package com.vendor.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vendor.dao.impl.ExpertDaoImpl;
import com.vendor.dao.impl.PackageDaoImpl;
import com.vendor.model.Package;
import com.vendor.model.ServiceExpert;
import com.vendor.service.ExpertService;
import com.vendor.service.PackageService;

@Service
public class RatingServiceImpl implements PackageService {

	@Autowired
	PackageDaoImpl repo;
	
	@Override
	public Package save(Package pack) {
		return repo.save(pack);
	}

	@Override
	public Package findById(Long id) {
		return repo.findById(id);
	}

	@Override
	public List<Package> findAll() {
		return repo.findAll();
	}

	@Override
	public void deleteById(Long id) {
		repo.deleteById(id);
	}

	
}
