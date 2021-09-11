package com.calculator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.calculator.dao.CurrencyCalculatorRepository;
import com.calculator.model.Currency;
import com.calculator.service.CurrencyCalculatorService;

@RestController
public class CurrencyCalculatorController {

	@Autowired
	CurrencyCalculatorService service;
	@Autowired
	Environment env;
	
	@GetMapping("curr-calc/from/{from}/to/{to}")
	public ResponseEntity<Currency> currencyCalculator(@PathVariable String from, @PathVariable String to) {
		Currency currency = service.findByFromCurrencyAndToCurrency(from, to);
		currency.setPort(Integer.valueOf(env.getProperty("server.port")));
		return ResponseEntity.ok(currency);
	}
	
	@PostMapping("curr-calc")
	public ResponseEntity<Currency> save(@RequestBody Currency currency) {
		return ResponseEntity.ok(service.save(currency));
	}
	
	@GetMapping("hello")
	public String hardCodedResponse() {
		return "Hello!! Docker";
	}
}
