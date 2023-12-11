package com.example.graphql.exception.handler;

import com.example.graphql.exception.ProblemAuthenticationException;
import com.netflix.graphql.dgs.exceptions.DefaultDataFetcherExceptionHandler;
import com.netflix.graphql.types.errors.TypedGraphQLError;
import graphql.execution.DataFetcherExceptionHandler;
import graphql.execution.DataFetcherExceptionHandlerParameters;
import graphql.execution.DataFetcherExceptionHandlerResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
public class ProblemGraphqlExceptionHandler implements DataFetcherExceptionHandler {

  private final DefaultDataFetcherExceptionHandler defaultHandler = new DefaultDataFetcherExceptionHandler();

  @Override
  public CompletableFuture<DataFetcherExceptionHandlerResult> handleException(DataFetcherExceptionHandlerParameters handlerParameters) {
    var exception = handlerParameters.getException();
    if (exception instanceof ProblemAuthenticationException) {
      var graphqlError = TypedGraphQLError.newBuilder()
              .message(exception.getMessage())
              .path(handlerParameters.getPath())
              .errorDetail(new ProblemErrorDetail())
//              .errorType(ErrorType.UNAUTHENTICATED)
              .build();

      var result = DataFetcherExceptionHandlerResult.newResult()
              .error(graphqlError)
              .build();

      return CompletableFuture.completedFuture(result);
    }
    return defaultHandler.handleException(handlerParameters);

  }
}
