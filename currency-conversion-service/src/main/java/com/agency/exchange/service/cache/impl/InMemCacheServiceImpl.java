package com.agency.exchange.service.cache.impl;

import com.agency.exchange.client.currency.model.ExchangeRateApiResponse;
import com.agency.exchange.service.cache.CacheService;
import com.github.benmanes.caffeine.cache.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class InMemCacheServiceImpl implements CacheService {

    @Autowired
    private Cache<String, BigDecimal> cache;

    @Override
    public void add(ExchangeRateApiResponse apiResponse) {
        apiResponse.getConversionRates().forEach((k, v) -> {
            cache.put(apiResponse.getBaseCode() + ":" + k, v);
        });
    }

    @Override
    public BigDecimal get(String key) {
        return cache.getIfPresent(key);
    }
}
