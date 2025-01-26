package com.agency.exchange.service.discounts;

import com.agency.exchange.configuration.Properties;
import com.agency.exchange.model.CurrencyConversionInput;
import com.agency.exchange.service.discounts.model.DiscountResult;

import java.math.BigDecimal;

public interface DiscountStrategy {
    DiscountResult applyDiscount(CurrencyConversionInput input, BigDecimal conversionRate, Properties properties);
}
