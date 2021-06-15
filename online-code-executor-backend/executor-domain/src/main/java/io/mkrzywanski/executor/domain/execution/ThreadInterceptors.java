package io.mkrzywanski.executor.domain.execution;

import java.io.PrintStream;

public final class ThreadInterceptors {
    public static ThreadInterceptor defaultInterceptor(final PrintStream printStream) {
        return new DefaultThreadInterceptor(printStream);
    }
}
