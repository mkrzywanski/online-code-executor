package io.mkrzywanski.executor.core.interceptor;

import java.lang.reflect.InvocationHandler;

public interface ThreadOutputInterceptor extends InvocationHandler {
    String getOutputForCurrentThread();
    void removeForCurrentThread();
}
