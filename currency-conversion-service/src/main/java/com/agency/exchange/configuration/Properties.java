package com.agency.exchange.configuration;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Configuration
@Getter
public class Properties {

    @Value("${currency.exchange.rate.api.connectTimeout}")
    private Integer exchangeRateApiConnectTimeout;
    @Value("${currency.exchange.rate.api.readTimeout}")
    private Integer exchangeRateApiReadTimeout;
    @Value("${currency.exchange.rate.api.retryCount}")
    private Integer exchangeRateApiRetryCount;
    @Value("${discount.storeEmployee}")
    private BigDecimal storeEmployeeDiscount;
    @Value("${discount.storeAffiliate}")
    private BigDecimal storeAffiliateDiscount;
    @Value("${discount.priorityCustomer}")
    private BigDecimal priorityCustomerDiscount;
    @Value("${discount.capping}")
    private BigDecimal discountCapping;
    @Value("${discount.minimum.discountValue}")
    private BigDecimal minimumValueForDiscountEligible;
}
