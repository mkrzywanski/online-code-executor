package io.mkrzywanski.onlinecodeexecutor.language.execution;

import io.mkrzywanski.onlinecodeexecutor.language.interceptor.ThreadOutputInterceptor;
import io.mkrzywanski.onlinecodeexecutor.language.execution.ExecutionException;
import io.mkrzywanski.onlinecodeexecutor.language.execution.Executor;
import io.mkrzywanski.onlinecodeexecutor.language.loading.LoadedClasses;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

@Singleton
public class DefaultExecutor implements Executor {

    private static final String MAIN = "main";
    private static final Object STRINGS = new String[]{};

    private final ThreadOutputInterceptor threadAwarePrintStream;

    @Inject
    public DefaultExecutor(final ThreadOutputInterceptor threadAwarePrintStream) {
        this.threadAwarePrintStream = threadAwarePrintStream;
    }

    @Override
    public ExecutionResult execute(final LoadedClasses loadedClasses) throws ExecutionException {
        Class<?> mainClass = loadedClasses.mainClass();
        return doExecute(mainClass);
    }

    private ExecutionResult doExecute(final Class<?> mainClass) throws ExecutionException {
        try {
            Method mainMethod = mainClass.getMethod(MAIN, String[].class);
            threadAwarePrintStream.removeForCurrentThread();
            mainMethod.invoke(null, (Object) STRINGS);
            return new ExecutionResult(threadAwarePrintStream.getOutputForCurrentThread());
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new ExecutionException("Execution failed", e);
        }
    }
}
