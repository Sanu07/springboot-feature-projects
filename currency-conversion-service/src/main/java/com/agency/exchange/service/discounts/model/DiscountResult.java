package com.agency.exchange.service.discounts.model;

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
public class DiscountResult {
    private String discountCategory;
    private BigDecimal finalAmount;
    private BigDecimal discountRate;
    private String discountType;
}
