package io.mkrzywanski.executor.app.infra.web.handler;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import io.mkrzywanski.executor.domain.compilation.exception.CompilationFailedException;

import javax.inject.Singleton;

@Produces
@Singleton
public class CompilationExceptionHandler implements ExceptionHandler<CompilationFailedException, HttpResponse<ErrorResponse>> {

    @Override
    public HttpResponse<ErrorResponse> handle(final HttpRequest request, final CompilationFailedException exception) {
        final ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.getCode(), exception.getMessage());
        return HttpResponse.badRequest(errorResponse);
    }
}
