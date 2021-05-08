package io.mkrzywanski.executor.core.execution;

import io.mkrzywanski.executor.core.interceptor.ThreadOutputInterceptor;
import io.mkrzywanski.executor.core.loading.LoadedClasses;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class DefaultExecutor implements Executor {

    private static final String MAIN = "main";
    private static final Object ARGUMENTS = new String[]{};

    private final ThreadOutputInterceptor threadAwarePrintStream;

    DefaultExecutor(final ThreadOutputInterceptor threadAwarePrintStream) {
        this.threadAwarePrintStream = threadAwarePrintStream;
    }

    @Override
    public ExecutionResult execute(final LoadedClasses loadedClasses) throws ExecutionException {
        final Class<?> mainClass = loadedClasses.mainClass();
        return executeClass(mainClass);
    }

    private ExecutionResult executeClass(final Class<?> mainClass) throws ExecutionException {
        try {
            return doExecute(mainClass);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new ExecutionException("Execution failed", e);
        }
    }

    private ExecutionResult doExecute(final Class<?> mainClass) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        final Method mainMethod = mainClass.getMethod(MAIN, String[].class);
        threadAwarePrintStream.removeForCurrentThread();
        mainMethod.invoke(null, ARGUMENTS);
        return new ExecutionResult(threadAwarePrintStream.getOutputForCurrentThread());
    }

}
