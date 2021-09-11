package com.conversion.model;

import lombok.Data;

@Data
public class Currency {

	private Long id;
	private String fromCurrency;
	private String toCurrency;
	private String convertedValue;
	private int port;
	private int currencyConversionPort;
}
