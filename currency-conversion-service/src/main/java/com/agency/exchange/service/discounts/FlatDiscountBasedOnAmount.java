package com.agency.exchange.service.discounts;

import com.agency.exchange.configuration.Properties;
import com.agency.exchange.model.CurrencyConversionInput;
import com.agency.exchange.service.discounts.model.DiscountResult;

import java.math.BigDecimal;

public class FlatDiscountBasedOnAmount implements DiscountStrategy {

    @Override
    public DiscountResult applyDiscount(CurrencyConversionInput input, BigDecimal conversionRate, Properties properties) {
        BigDecimal discountAmount = input.getAmount().multiply(conversionRate);
        if (discountAmount.compareTo(properties.getMinimumValueForDiscountEligible()) > 0) {
            discountAmount = discountAmount.subtract(properties.getDiscountCapping().multiply(conversionRate));
        }
        return DiscountResult.builder()
                .discountCategory("FLAT_DISCOUNT_BASED_ON_AMOUNT")
                .finalAmount(discountAmount)
                .discountRate(properties.getDiscountCapping())
                .discountType("FLAT_VALUE")
                .build();
    }
}
