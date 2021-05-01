package io.mkrzywanski.executor.core.interceptor;

import java.io.PrintStream;

public final class ThreadOutputInterceptors {

    private ThreadOutputInterceptors() {
    }

    public static ThreadOutputInterceptor forPrintStream(final PrintStream printStream) {
        return new ThreadOutputPrintStreamInterceptor(printStream);
    }
}
