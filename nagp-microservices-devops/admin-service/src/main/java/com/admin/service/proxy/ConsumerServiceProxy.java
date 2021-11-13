package com.admin.service.proxy;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.admin.model.Customer;

@FeignClient(name = "consumer-service")
public interface ConsumerServiceProxy {

	@GetMapping("customers")
	public List<Customer> findAll();
	
	@GetMapping("customers/{id}")
	public Customer findById(@PathVariable(value = "id") Long id);
}
