package io.mkrzywanski.onlinecodeexecutor.web.handler;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import io.mkrzywanski.onlinecodeexecutor.language.LanguageNotSupportedException;
import io.mkrzywanski.onlinecodeexecutor.web.ErrorOutput;

import javax.inject.Singleton;

@Singleton
public class LanguageNotSupportedExceptionHandler implements ExceptionHandler<LanguageNotSupportedException, HttpResponse<ErrorOutput>> {
    @Override
    public HttpResponse<ErrorOutput> handle(HttpRequest request, LanguageNotSupportedException exception) {
        ErrorOutput errorOutput = new ErrorOutput(400, exception.getMessage());
        return HttpResponse.badRequest(errorOutput);
    }
}
