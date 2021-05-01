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
public class CompilationExceptionHandler implements ExceptionHandler<CompilationFailedException, HttpResponse<ErrorOutput>> {

    @Override
    public HttpResponse<ErrorOutput> handle(final HttpRequest request, final CompilationFailedException exception) {
        final ErrorOutput errorOutput = new ErrorOutput(HttpStatus.BAD_REQUEST.getCode(), exception.getReport());
        return HttpResponse.badRequest(errorOutput);
    }
}
