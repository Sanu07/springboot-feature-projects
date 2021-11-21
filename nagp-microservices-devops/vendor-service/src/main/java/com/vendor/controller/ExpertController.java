package com.vendor.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vendor.enums.Service;
import com.vendor.model.ServiceExpert;
import com.vendor.service.AdminServiceProxy;
import com.vendor.service.impl.ExpertServiceImpl;

@RestController
@RequestMapping("vendors")
public class ExpertController {

	@Autowired
	ExpertServiceImpl service;
	
	@Autowired
	AdminServiceProxy adminProxy;
	
	@GetMapping
	public ResponseEntity<List<ServiceExpert>> getAllExperts() {
		return ResponseEntity.ok(service.findAll());
	}
	
	@GetMapping("{id}")
	public ResponseEntity<ServiceExpert> getExpert(@PathVariable Long id) {
		return ResponseEntity.ok(service.findById(id));
	}
	
	@PostMapping
	public ResponseEntity<ServiceExpert> saveExpert(@RequestBody ServiceExpert expert) {
		ServiceExpert expert2 = service.save(expert);
		adminProxy.checkForNoResponseBookings(expert2);
		return ResponseEntity.status(HttpStatus.CREATED).body(expert2);
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<Void> deleteExpert(@PathVariable Long id) {
		service.deleteById(id);
		return ResponseEntity.status(HttpStatus.ACCEPTED).build();
	}
	
	@GetMapping("profession/{professions}")
	public ResponseEntity<List<ServiceExpert>> findExpertsByProfession(@PathVariable List<Service> professions) {
		return ResponseEntity.ok(service.findExpertsByProfession(professions));
	}
}
