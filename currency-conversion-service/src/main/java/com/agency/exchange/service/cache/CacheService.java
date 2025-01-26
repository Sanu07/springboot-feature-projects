package com.agency.exchange.service.cache;

import com.agency.exchange.client.currency.model.ExchangeRateApiResponse;

import java.math.BigDecimal;

public interface CacheService {
    void add(ExchangeRateApiResponse apiResponse);
    BigDecimal get(String key);
}
