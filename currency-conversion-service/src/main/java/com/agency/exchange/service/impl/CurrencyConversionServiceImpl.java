package com.agency.exchange.service.impl;

import com.agency.exchange.client.currency.ExchangeRateApi;
import com.agency.exchange.client.currency.model.ExchangeRateApiResponse;
import com.agency.exchange.configuration.Properties;
import com.agency.exchange.exception.CurrencyConversionServiceException;
import com.agency.exchange.model.CurrencyConversionInput;
import com.agency.exchange.model.CurrencyConversionResponse;
import com.agency.exchange.service.CurrencyConversionService;
import com.agency.exchange.service.cache.CacheService;
import com.agency.exchange.service.discounts.*;
import com.agency.exchange.service.discounts.model.DiscountResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class CurrencyConversionServiceImpl implements CurrencyConversionService {

    private final List<DiscountStrategy> discountStrategies;

    @Autowired
    private Properties properties;

    public CurrencyConversionServiceImpl() {
        discountStrategies = new ArrayList<>();
        discountStrategies.add(new StoreEmployeeDiscount());
        discountStrategies.add(new StoreAffiliateDiscount());
        discountStrategies.add(new PriorityCustomerDiscount());
        discountStrategies.add(new FlatDiscountBasedOnAmount());
    }

    @Autowired
    private ExchangeRateApi exchangeRateApi;

    @Autowired
    private CacheService cacheService;

    @Override
    public CurrencyConversionResponse calculate(CurrencyConversionInput input) {
        try {
            BigDecimal conversionRate = cacheService.get(input.getCurrencyFrom() + ":" + input.getCurrencyTo());
            if (Objects.isNull(conversionRate)) {
                ExchangeRateApiResponse exchangeRates = exchangeRateApi.getExchangeRates(input.getCurrencyFrom());
                conversionRate = exchangeRates.getConversionRates().get(input.getCurrencyTo());
                cacheService.add(exchangeRates);
            }

            DiscountResult discountResult = calculateDiscountAmount(input, conversionRate);
            return CurrencyConversionResponse.builder()
                    .currencyTo(input.getCurrencyTo())
                    .currencyFrom(input.getCurrencyFrom())
                    .finalAmount(discountResult.getFinalAmount().setScale(2, RoundingMode.HALF_UP))
                    .appliedDiscount(discountResult.getDiscountRate())
                    .subTotalBeforeDiscount(input.getAmount().setScale(2, RoundingMode.HALF_UP))
                    .discountType(discountResult.getDiscountType())
                    .build();
        } catch (Exception e) {
            log.error("CurrencyConversionServiceImpl::calculate error received for input: {}", input, e);
            throw new CurrencyConversionServiceException(e.getMessage(), e);
        }
    }

    private DiscountResult calculateDiscountAmount(CurrencyConversionInput input, BigDecimal conversionRate) {
        DiscountResult minAmountToBePaid = null;

        if (!input.isHasOnlyGroceries()) {
            for (DiscountStrategy strategy : discountStrategies) {
                DiscountResult result = strategy.applyDiscount(input, conversionRate, properties);
                if (result != null) {
                    if (minAmountToBePaid == null || result.getFinalAmount().compareTo(minAmountToBePaid.getFinalAmount()) < 0) {
                        minAmountToBePaid = result;
                    }
                }
            }
        }

        if (minAmountToBePaid != null) {
            return minAmountToBePaid;
        }
        return DiscountResult.builder()
                .discountRate(BigDecimal.ZERO)
                .discountCategory("NO_DISCOUNT")
                .finalAmount(input.getAmount().multiply(conversionRate))
                .build();
    }
}
