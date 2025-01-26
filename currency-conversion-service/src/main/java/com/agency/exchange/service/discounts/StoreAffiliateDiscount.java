package com.agency.exchange.service.discounts;

import com.agency.exchange.configuration.Properties;
import com.agency.exchange.model.CurrencyConversionInput;
import com.agency.exchange.service.discounts.model.DiscountResult;

import java.math.BigDecimal;

public class StoreAffiliateDiscount implements DiscountStrategy {

    @Override
    public DiscountResult applyDiscount(CurrencyConversionInput input, BigDecimal conversionRate, Properties properties) {
        if (input.isStoreAffiliate()) {
            BigDecimal discountAmount = input.getAmount().multiply(conversionRate).multiply(BigDecimal.ONE.subtract(properties.getStoreAffiliateDiscount()));
            return DiscountResult.builder()
                    .discountCategory("STORE_AFFILIATE_DISCOUNT")
                    .finalAmount(discountAmount)
                    .discountRate(properties.getStoreAffiliateDiscount().multiply(new BigDecimal("100.0")))
                    .discountType("PERCENTAGE")
                    .build();
        }
        return null;
    }
}
