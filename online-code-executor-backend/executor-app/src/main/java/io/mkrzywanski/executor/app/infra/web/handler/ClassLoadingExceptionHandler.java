//package io.mkrzywanski.executor.app.infra.web.handler;
//
//import io.micronaut.http.HttpRequest;
//import io.micronaut.http.HttpResponse;
//import io.micronaut.http.server.exceptions.ExceptionHandler;
//import io.mkrzywanski.executor.core.loading.ClassLoadingException;
//
//class ClassLoadingExceptionHandler implements ExceptionHandler<ClassLoadingException, HttpResponse<ErrorOutput>> {
//    @Override
//    public HttpResponse<ErrorOutput> handle(final HttpRequest request, final ClassLoadingException exception) {
//        final ErrorOutput errorOutput = new ErrorOutput(500, exception.getMessage());
//        return HttpResponse.serverError(errorOutput);
//    }
//}
