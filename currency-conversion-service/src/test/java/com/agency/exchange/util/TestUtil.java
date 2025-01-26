package com.agency.exchange.util;

import com.agency.exchange.model.CurrencyConversionInput;

import java.math.BigDecimal;

public class TestUtil {

    public static CurrencyConversionInput getCurrencyConversionInput() {
        return CurrencyConversionInput.builder()
                .amount(new BigDecimal("100"))
                .currencyFrom("INR")
                .currencyTo("INR")
                .build();
    }
}
