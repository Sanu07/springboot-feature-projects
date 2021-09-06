package com.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.security.model.LoginDetails;
import com.security.model.User;
import com.security.service.UserService;

@RestController
public class NonSecuredController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	PasswordEncoder encoder;
	
	@PostMapping("register")
	public ResponseEntity<User> register(@RequestBody User user) {
		user.setPassword(encoder.encode(user.getPassword()));
		return ResponseEntity.ok().body(userService.register(user));
	}
	
	@PostMapping("login")
	public ResponseEntity<String> login(@RequestBody LoginDetails loginDetails) {
		return null;
	}
	
	@GetMapping("data")
	public String getUnprotectedData() {
		return "Unprotected data";
	}
}
