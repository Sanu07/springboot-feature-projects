package com.test.graphql.exception;

public class UserNotFoundException extends BaseException {
    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(String message, CustomErrorType errorType) {
        super(message, errorType);
    }
}
