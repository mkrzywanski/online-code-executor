package io.mkrzywanski.executor.core.execution;

import io.mkrzywanski.executor.core.interceptor.ThreadOutputInterceptor;

public final class Executors {
    public static Executor defaultExecutor(final ThreadOutputInterceptor interceptor) {
        return new DefaultExecutor(interceptor);
    }
}
