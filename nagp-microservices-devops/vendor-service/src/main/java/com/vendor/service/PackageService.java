package com.vendor.service;

import java.util.List;

import com.vendor.model.Package;

public interface PackageService {

	Package save(Package pack);
	Package findById(Long id);
	List<Package> findAll();
	void deleteById(Long id);
}
