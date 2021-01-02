package io.mkrzywanski.onlinecodeexecutor.web.handler;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import io.mkrzywanski.onlinecodeexecutor.language.loading.ClassLoadingException;
import io.mkrzywanski.onlinecodeexecutor.web.ErrorOutput;

public class ClassLoadingExceptionHandler implements ExceptionHandler<ClassLoadingException, HttpResponse<ErrorOutput>> {
    @Override
    public HttpResponse<ErrorOutput> handle(HttpRequest request, ClassLoadingException exception) {
        ErrorOutput errorOutput = new ErrorOutput(500, exception.getMessage());
        return HttpResponse.serverError(errorOutput);
    }
}
