package io.mkrzywanski.onlinecodeexecutor.language.interceptor;

public interface ThreadOutputInterceptor {
    String getOutputForCurrentThread();
    void removeForCurrentThread();
}
