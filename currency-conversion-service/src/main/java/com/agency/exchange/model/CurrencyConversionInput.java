package com.agency.exchange.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CurrencyConversionInput {
    @NotNull(message = "currencyTo cannot be null")
    private String currencyTo;
    @NotNull(message = "currencyFrom cannot be null")
    private String currencyFrom;
    @NotNull(message = "amount cannot be null")
    private BigDecimal amount;
    @JsonProperty("isStoreEmployee")
    private boolean isStoreEmployee;
    @JsonProperty("isStoreAffiliate")
    private boolean isStoreAffiliate;
    @JsonProperty("isPriorityCustomer")
    private boolean isPriorityCustomer;
    @JsonProperty("hasOnlyGroceries")
    private boolean hasOnlyGroceries;
}
