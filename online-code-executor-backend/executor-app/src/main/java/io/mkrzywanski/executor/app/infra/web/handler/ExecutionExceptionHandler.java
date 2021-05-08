package io.mkrzywanski.executor.app.infra.web.handler;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;

import io.micronaut.http.server.exceptions.ExceptionHandler;
import io.mkrzywanski.executor.domain.execution.exception.ExecutionFailedException;

import javax.inject.Singleton;

@Singleton
public class ExecutionExceptionHandler implements ExceptionHandler<ExecutionFailedException, HttpResponse<ErrorOutput>> {
    @Override
    public HttpResponse<ErrorOutput> handle(final HttpRequest request, final ExecutionFailedException exception) {
        final ErrorOutput errorOutput = new ErrorOutput(HttpStatus.BAD_REQUEST.getCode(), exception.getMessage());
        return HttpResponse.badRequest(errorOutput);
    }
}