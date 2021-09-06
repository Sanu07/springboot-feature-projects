package com.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("protected")
public class ProtectedController {

	@GetMapping("data")
	public String getProtectedResource() {
		return this.getClass().getCanonicalName();
	}
}
