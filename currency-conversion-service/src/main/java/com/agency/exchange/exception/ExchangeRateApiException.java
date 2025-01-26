package com.agency.exchange.exception;

public class ExchangeRateApiException extends RuntimeException {

    public ExchangeRateApiException(String message, Throwable ex) {
        super(message, ex);
    }
}
