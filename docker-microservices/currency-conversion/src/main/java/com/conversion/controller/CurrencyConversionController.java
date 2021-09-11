package com.conversion.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.conversion.model.Currency;
import com.conversion.service.CurrencyCalculatorServiceProxy;


@RestController
public class CurrencyConversionController {
	
	@Autowired
	Environment env;
	
	@Autowired
	CurrencyCalculatorServiceProxy proxy;

	@GetMapping("/from/{from}/to/{to}")
	public ResponseEntity<Currency> currencyConversion(@PathVariable String from, @PathVariable String to) {
		Map<String, String> map = new HashMap<>();
		map.put("from", from);
		map.put("to", to);
		Currency currency = new RestTemplate().getForEntity("${CURRENCY_CALCULATOR_URI:http://localhost:8000/curr-calc/from/{from}/to/{to}}", Currency.class, map).getBody();
		if (currency != null) {
			currency.setCurrencyConversionPort(Integer.valueOf(env.getProperty("server.port")));
		}
		return ResponseEntity.ok(currency);
	}
	
	@GetMapping("feign/from/{from}/to/{to}")
	public ResponseEntity<Currency> currencyConversionFeign(@PathVariable String from, @PathVariable String to) {
		Currency currency = proxy.currencyCalculator(from, to).getBody();
		currency.setCurrencyConversionPort(Integer.valueOf(env.getProperty("server.port")));
		return ResponseEntity.ok(currency);
	}
}
