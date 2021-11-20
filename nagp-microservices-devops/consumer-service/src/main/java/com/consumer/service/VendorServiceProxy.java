package com.consumer.service;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.consumer.model.Package;
import com.consumer.model.ServiceExpert;

@FeignClient(name = "vendor-service")
public interface VendorServiceProxy {

	@GetMapping("vendors")
	public List<ServiceExpert> findAllExperts();
	
	@GetMapping("vendors/packages")
	public List<Package> findAllPackages();
}
