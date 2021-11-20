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

import com.vendor.model.Package;
import com.vendor.service.impl.PackageServiceImpl;

@RestController
@RequestMapping("vendors/packages")
public class PackageController {

	@Autowired
	PackageServiceImpl service;
	
	@GetMapping
	public ResponseEntity<List<Package>> getAllExperts() {
		return ResponseEntity.ok(service.findAll());
	}
	
	@GetMapping("{id}")
	public ResponseEntity<Package> getExpert(@PathVariable Long id) {
		return ResponseEntity.ok(service.findById(id));
	}
	
	@PostMapping
	public ResponseEntity<Package> saveExpert(@RequestBody Package expert) {
		return ResponseEntity.status(HttpStatus.CREATED).body(service.save(expert));
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<Void> deleteExpert(@PathVariable Long id) {
		service.deleteById(id);
		return ResponseEntity.status(HttpStatus.ACCEPTED).build();
	}
}
