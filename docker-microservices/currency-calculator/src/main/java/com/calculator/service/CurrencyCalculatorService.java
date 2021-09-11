package com.calculator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.calculator.dao.CurrencyCalculatorRepository;
import com.calculator.model.Currency;

@Service
public class CurrencyCalculatorService {

	@Autowired
	CurrencyCalculatorRepository repo;
	
	public Currency findByFromCurrencyAndToCurrency(String fromCurrency, String toCurrency) {
		return repo.findByFromCurrencyAndToCurrency(fromCurrency, toCurrency);
	}

	public Currency save(Currency currency) {
		return repo.save(currency);
	}
}
