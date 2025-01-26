package com.agency.exchange.client.currency;

import com.agency.exchange.client.currency.model.ExchangeRateApiResponse;
import com.agency.exchange.exception.ExchangeRateApiException;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
public class ExchangeRateApi {

    @Autowired
    @Qualifier("exchangeRateApiRestTemplate")
    private RestTemplate restTemplate;

    @Value("${currency.exchange.rate.api.v6}")
    private String exchangeRateApiUrl;

    @Retryable(
            exclude = {BadRequestException.class,
                    HttpServerErrorException.NotImplemented.class,
                    HttpServerErrorException.InternalServerError.class},
            maxAttempts = 3,
            backoff = @Backoff(delay = 200, multiplier = 2)
    )
    public ExchangeRateApiResponse getExchangeRates(String currencySymbol) {
        String url = String.join("", exchangeRateApiUrl, currencySymbol);
        try {
            ExchangeRateApiResponse exchangeRateApiResponse = restTemplate.getForObject(url, ExchangeRateApiResponse.class);
            log.info("ExchangeRateApiResponse received for {}", currencySymbol);
            return exchangeRateApiResponse;
        } catch (Exception e) {
            log.error("ExchangeRateApi::getExchangeRates error occurred while fetching data for {}. error message {}",
                    currencySymbol, e.getMessage(), e);
            throw new ExchangeRateApiException(e.getMessage(), e);
        }
    }
}
