package com.conversion.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.conversion.model.Currency;

@FeignClient(name = "currency-calculator")
public interface CurrencyCalculatorServiceProxy {

	@GetMapping("curr-calc/from/{from}/to/{to}")
	public ResponseEntity<Currency> currencyCalculator(@PathVariable String from, @PathVariable String to);
}
