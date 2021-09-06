package com.security.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.security.model.User;
import com.security.service.UserService;

@RestController
@RequestMapping("admin")
public class SecuredController {

	@Autowired
	UserService userService;
	
	@GetMapping("data")
	public String getProtectedResource() {
		return this.getClass().getCanonicalName() + " [Only admin can see]";
	}
	
	@GetMapping("users")
	public ResponseEntity<List<User>> getUsers() {
		return ResponseEntity.ok(userService.getUsers());
	}
}
