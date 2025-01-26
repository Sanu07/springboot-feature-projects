package com.agency.exchange.service;

import com.agency.exchange.model.CurrencyConversionInput;
import com.agency.exchange.model.CurrencyConversionResponse;

public interface CurrencyConversionService {

    CurrencyConversionResponse calculate(CurrencyConversionInput input);
}
