package io.mkrzywanski.executor.app.infra.web.handler;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;

import io.micronaut.http.server.exceptions.ExceptionHandler;
import io.mkrzywanski.executor.domain.execution.exception.ExecutionFailedException;

import javax.inject.Singleton;

@Singleton
public class ExecutionExceptionHandler implements ExceptionHandler<ExecutionFailedException, HttpResponse<ErrorResponse>> {
    @Override
    public HttpResponse<ErrorResponse> handle(final HttpRequest request, final ExecutionFailedException exception) {
        final ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.getCode(), exception.getMessage());
        return HttpResponse.badRequest(errorResponse);
    }
}
