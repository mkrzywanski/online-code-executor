package io.mkrzywanski.onlinecodeexecutor.language.execution;

import io.mkrzywanski.onlinecodeexecutor.language.interceptor.ThreadOutputInterceptor;
import io.mkrzywanski.onlinecodeexecutor.language.loading.LoadedClasses;
import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Singleton
public class DefaultExecutor implements Executor {

    private static final String MAIN = "main";
    private static final Object ARGUMENTS = new String[]{};

    private final ThreadOutputInterceptor threadAwarePrintStream;

    @Inject
    public DefaultExecutor(final ThreadOutputInterceptor threadAwarePrintStream) {
        this.threadAwarePrintStream = threadAwarePrintStream;
    }

    @Override
    public ExecutionResult execute(final LoadedClasses loadedClasses) throws ExecutionException {
        Class<?> mainClass = loadedClasses.mainClass();
        return executeClass(mainClass);
    }

    private ExecutionResult executeClass(final Class<?> mainClass) throws ExecutionException {
        try {
            return doExecute(mainClass);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new ExecutionException("Execution failed", e);
        }
    }

    private ExecutionResult doExecute(Class<?> mainClass) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Method mainMethod = mainClass.getMethod(MAIN, String[].class);
        threadAwarePrintStream.removeForCurrentThread();
        mainMethod.invoke(null, ARGUMENTS);
        return new ExecutionResult(threadAwarePrintStream.getOutputForCurrentThread());
    }

}
