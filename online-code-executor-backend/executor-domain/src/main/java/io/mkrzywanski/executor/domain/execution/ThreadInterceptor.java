package io.mkrzywanski.executor.domain.execution;

import java.lang.reflect.InvocationHandler;

public interface ThreadInterceptor extends InvocationHandler {
    String getOutputForCurrentThread();
    void removeForCurrentThread();
}
