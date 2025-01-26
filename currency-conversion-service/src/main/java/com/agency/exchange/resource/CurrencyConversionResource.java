package com.agency.exchange.resource;

import com.agency.exchange.model.CurrencyConversionInput;
import com.agency.exchange.model.CurrencyConversionResponse;
import com.agency.exchange.service.CurrencyConversionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/api")
public class CurrencyConversionResource {

    @Autowired
    private CurrencyConversionService currencyConversionService;

    @PostMapping("calculate")
    public ResponseEntity<CurrencyConversionResponse> calculate(@RequestBody CurrencyConversionInput currencyConversionInput) {
        CurrencyConversionResponse response = currencyConversionService.calculate(currencyConversionInput);
        return ResponseEntity.ok(response);
    }
}
