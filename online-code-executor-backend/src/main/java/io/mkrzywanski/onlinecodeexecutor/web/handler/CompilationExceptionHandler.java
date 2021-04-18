package io.mkrzywanski.onlinecodeexecutor.web.handler;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import io.mkrzywanski.onlinecodeexecutor.language.compilation.CompilationException;
import io.mkrzywanski.onlinecodeexecutor.web.ErrorOutput;

import javax.inject.Singleton;

@Produces
@Singleton
public class CompilationExceptionHandler implements ExceptionHandler<CompilationException, HttpResponse<ErrorOutput>> {

    @Override
    public HttpResponse<ErrorOutput> handle(final HttpRequest request, final CompilationException exception) {
        final ErrorOutput errorOutput = new ErrorOutput(HttpStatus.BAD_REQUEST.getCode(), exception.getReport());
        return HttpResponse.badRequest(errorOutput);
    }
}
