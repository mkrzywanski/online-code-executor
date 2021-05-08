package io.mkrzywanski.executor.domain.execution.interceptor;

import io.mkrzywanski.executor.core.interceptor.ThreadOutputInterceptors;

import java.io.PrintStream;
import java.lang.reflect.InvocationHandler;

public final class Interceptors {
    public static InvocationHandler newHandler(final PrintStream printStream) {
        return ThreadOutputInterceptors.forPrintStream(printStream);
    }
}
