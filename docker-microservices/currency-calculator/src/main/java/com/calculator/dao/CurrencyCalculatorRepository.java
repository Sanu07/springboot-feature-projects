package com.calculator.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.calculator.model.Currency;

public interface CurrencyCalculatorRepository extends JpaRepository<Currency, Long> {
	
	public Currency findByFromCurrencyAndToCurrency(String fromCurrency, String toCurrency);
}
