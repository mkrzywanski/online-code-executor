package io.mkrzywanski.executor.domain.execution;

import io.mkrzywanski.executor.core.interceptor.ThreadOutputInterceptor;

import java.lang.reflect.Method;

public class ThreadOutputInterceptorAdapter implements ThreadOutputInterceptor {

    private final ThreadInterceptor threadInterceptor;

    public ThreadOutputInterceptorAdapter(final ThreadInterceptor threadInterceptor) {
        this.threadInterceptor = threadInterceptor;
    }

    @Override
    public String getOutputForCurrentThread() {
        return threadInterceptor.getOutputForCurrentThread();
    }

    @Override
    public void removeForCurrentThread() {
        threadInterceptor.removeForCurrentThread();
    }

    @Override
    public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
        return threadInterceptor.invoke(proxy, method, args);
    }
}
