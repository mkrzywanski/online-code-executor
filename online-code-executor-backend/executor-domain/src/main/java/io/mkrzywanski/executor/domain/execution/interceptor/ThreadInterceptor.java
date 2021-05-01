package io.mkrzywanski.executor.domain.execution.interceptor;

import java.lang.reflect.InvocationHandler;

public interface ThreadInterceptor extends InvocationHandler {
    String getOutputForCurrentThread();
    void removeForCurrentThread();
}
