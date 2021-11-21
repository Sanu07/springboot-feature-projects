package com.consumer.service;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.consumer.model.Package;
import com.consumer.model.ServiceExpert;

@FeignClient(name = "vendor-service")
public interface VendorServiceProxy {

	@GetMapping("vendors")
	public List<ServiceExpert> findAllExperts();
	
	@GetMapping("vendors/packages")
	public List<Package> findAllPackages();
	
	@GetMapping("vendors/{id}")
	public ResponseEntity<ServiceExpert> getExpert(@PathVariable (value = "id") Long id);
	
	@PostMapping("vendors")
	public ResponseEntity<ServiceExpert> saveExpert(@RequestBody ServiceExpert expert);
}
