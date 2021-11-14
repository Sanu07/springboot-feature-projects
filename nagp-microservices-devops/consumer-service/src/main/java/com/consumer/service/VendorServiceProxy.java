package com.consumer.service;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.consumer.model.Package;
import com.consumer.model.ServiceExpert;

@FeignClient(name = "vendor-service")
public interface VendorServiceProxy {

	@GetMapping("experts")
	public List<ServiceExpert> findAllExperts();
	
	@GetMapping("packages")
	public List<Package> findAllPackages();
}
