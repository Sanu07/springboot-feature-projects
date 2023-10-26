package com.test.graphql.exception;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.test.graphql.generated.DgsConstants;

@DgsComponent
public class ExceptionResolver {

    @DgsQuery(field = DgsConstants.QUERY.DefaultException)
    public boolean defaultException() {
        throw new RuntimeException("Error message");
    }

    @DgsQuery(field = DgsConstants.QUERY.CustomisedException)
    public boolean customisedException() {
        throw new UserNotFoundException("User not found custom message", BaseException.CustomErrorType.NO_USER_FOUND);
    }
}
