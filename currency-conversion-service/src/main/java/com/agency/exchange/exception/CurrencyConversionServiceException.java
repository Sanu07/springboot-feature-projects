package com.agency.exchange.exception;

public class CurrencyConversionServiceException extends RuntimeException {

    public CurrencyConversionServiceException(String message, Throwable ex) {
        super(message, ex);
    }
}
