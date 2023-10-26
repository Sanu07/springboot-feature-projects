package com.test.graphql.exception;

import com.netflix.graphql.types.errors.TypedGraphQLError;
import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.execution.DataFetcherExceptionHandler;
import graphql.execution.DataFetcherExceptionHandlerParameters;
import graphql.execution.DataFetcherExceptionHandlerResult;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Component
public class CustomDataFetchingExceptionHandler implements DataFetcherExceptionHandler {

   @Override
   public CompletableFuture<DataFetcherExceptionHandlerResult> handleException(DataFetcherExceptionHandlerParameters handlerParameters) {
      if (handlerParameters.getException() instanceof UserNotFoundException userNotFoundException) {

         final GraphQLError graphQLError =
                 GraphqlErrorBuilder.newError()
                         .message(userNotFoundException.getMessage())
                         .path(handlerParameters.getPath())
                         .locations(Collections.singletonList(handlerParameters.getSourceLocation()))
                         .extensions(userNotFoundException.getExtensions())
                         .errorType(userNotFoundException.getErrorType())
                         .build();

         DataFetcherExceptionHandlerResult result = DataFetcherExceptionHandlerResult.newResult()
                 .error(graphQLError)
                 .build();

         return CompletableFuture.completedFuture(result);
      } else {
         return DataFetcherExceptionHandler.super.handleException(handlerParameters);
      }
   }
}