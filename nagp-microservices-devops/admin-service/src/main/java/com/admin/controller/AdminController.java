package com.admin.controller;

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

import com.admin.model.Admin;
import com.admin.service.impl.AdminServiceImpl;

@RestController
@RequestMapping("admin")
public class AdminController {

	@Autowired
	private AdminServiceImpl service;

	@GetMapping
	public ResponseEntity<List<Admin>> getAllAdmins() {
		return ResponseEntity.ok(service.findAll());
	}

	@GetMapping("{id}")
	public ResponseEntity<Admin> getAdmin(@PathVariable Long id) {
		return ResponseEntity.ok(service.findById(id));
	}

	@PostMapping
	public ResponseEntity<Admin> saveAdmin(@RequestBody Admin admin) {
		return ResponseEntity.ok(service.save(admin));
	}

	@DeleteMapping("{id}")
	public ResponseEntity<Void> deleteAdmin(@PathVariable Long id) {
		service.deleteById(id);
		return ResponseEntity.status(HttpStatus.ACCEPTED).build();
	}
}
