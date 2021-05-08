package io.mkrzywanski.executor.domain.execution.interceptor;

import io.mkrzywanski.executor.core.interceptor.ThreadOutputInterceptor;
import io.mkrzywanski.executor.core.interceptor.ThreadOutputInterceptors;

import java.io.PrintStream;
import java.lang.reflect.Method;

public class DefaultThreadInterceptor implements ThreadInterceptor {

    private final ThreadOutputInterceptor threadOutputInterceptor;

    public DefaultThreadInterceptor(final PrintStream printStream) {
        threadOutputInterceptor = ThreadOutputInterceptors.forPrintStream(printStream);
    }

    @Override
    public String getOutputForCurrentThread() {
        return threadOutputInterceptor.getOutputForCurrentThread();
    }

    @Override
    public void removeForCurrentThread() {
        threadOutputInterceptor.removeForCurrentThread();
    }

    @Override
    public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
        return threadOutputInterceptor.invoke(proxy, method, args);
    }
}
