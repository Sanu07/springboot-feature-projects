package com.test.graphql.exception;

import graphql.ErrorClassification;
import graphql.ErrorType;
import graphql.GraphQLError;
import graphql.language.SourceLocation;

import java.util.Collections;
import java.util.List;

public class BaseException extends RuntimeException implements GraphQLError {

    private CustomErrorType errorType;

    public BaseException(String message) {
        super(message);
    }

    public BaseException(String message, CustomErrorType errorType) {
        super(message);
        this.errorType = errorType;
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

    @Override
    public List<SourceLocation> getLocations() {
        return Collections.emptyList();
    }

    @Override
    public ErrorClassification getErrorType() {
        if (this.errorType != null) {
            return this.errorType;
        }
        return ErrorType.DataFetchingException;
    }

    public enum CustomErrorType implements ErrorClassification{
        NO_USER_FOUND
    }
}
