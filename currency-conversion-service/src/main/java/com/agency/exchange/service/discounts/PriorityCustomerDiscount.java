package com.agency.exchange.service.discounts;

import com.agency.exchange.configuration.Properties;
import com.agency.exchange.model.CurrencyConversionInput;
import com.agency.exchange.service.discounts.model.DiscountResult;

import java.math.BigDecimal;

public class PriorityCustomerDiscount implements DiscountStrategy {

    @Override
    public DiscountResult applyDiscount(CurrencyConversionInput input, BigDecimal conversionRate, Properties properties) {
        if (input.isPriorityCustomer()) {
            BigDecimal discountAmount = input.getAmount().multiply(conversionRate).multiply(BigDecimal.ONE.subtract(properties.getPriorityCustomerDiscount()));
            return DiscountResult.builder()
                    .discountCategory("PRIORITY_CUSTOMER_DISCOUNT")
                    .finalAmount(discountAmount)
                    .discountRate(properties.getPriorityCustomerDiscount().multiply(new BigDecimal("100.0")))
                    .discountType("PERCENTAGE")
                    .build();
        }
        return null;
    }
}
