package com.admin.proxy.service;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.admin.enums.Service;
import com.admin.model.ServiceExpert;

@FeignClient(name = "vendor-service")
public interface VendorServiceProxy {

	@GetMapping("vendors")
	public List<ServiceExpert> findAll();
	
	@GetMapping("vendors/{id}")
	public ServiceExpert findById(@PathVariable(value = "id") Long id);                                                             
	
	@GetMapping("vendors/profession/{professions}")
	public List<ServiceExpert> findExpertsByProfession(@PathVariable(value = "professions") List<Service> services);
}
