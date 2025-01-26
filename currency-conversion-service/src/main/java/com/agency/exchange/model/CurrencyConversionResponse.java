package com.agency.exchange.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CurrencyConversionResponse {
    private String currencyTo;
    private String currencyFrom;
    private BigDecimal subTotalBeforeDiscount;
    private BigDecimal finalAmount;
    private BigDecimal appliedDiscount;
    private String discountType;
}
