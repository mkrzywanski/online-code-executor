package io.mkrzywanski.onlinecodeexecutor.language.groovy;

import io.mkrzywanski.onlinecodeexecutor.language.interceptor.ThreadOutputInterceptor;
import io.mkrzywanski.onlinecodeexecutor.language.interceptor.ThreadOutputPrintStreamInterceptor;
import io.mkrzywanski.onlinecodeexecutor.language.execution.ExecutionException;
import io.mkrzywanski.onlinecodeexecutor.language.execution.Executor;

import javax.inject.Inject;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

public class GroovyExecutor implements Executor {
    private static final String MAIN = "main";
    private static final String EXECUTION_FAILED = "Execution failed";

    private final ThreadOutputInterceptor threadAwarePrintStream;

    @Inject
    public GroovyExecutor(final ThreadOutputInterceptor threadAwarePrintStream) {
        this.threadAwarePrintStream = threadAwarePrintStream;
    }

    @Override
    public String execute(final Set<Class<?>> loadedClasses) throws ExecutionException {
        Class<?> mainClass = findMainClass(loadedClasses)
                .orElseThrow(ExecutionException::noMainMethod);

        return doExecute(mainClass);
    }

    private String doExecute(final Class<?> mainClass) throws ExecutionException {
        try {
            Method mainMethod = mainClass.getMethod(MAIN, String[].class);
            threadAwarePrintStream.removeForCurrentThread();
            mainMethod.invoke(null, (Object) new String[]{});
            return threadAwarePrintStream.getOutputForCurrentThread();
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new ExecutionException(EXECUTION_FAILED, e);
        }
    }

    private Optional<Class<?>> findMainClass(Set<Class<?>> classes) {
        return classes.stream()
                .filter(this::isMainClass)
                .findFirst();
    }

    private boolean isMainClass(Class<?> clazz) {
        Method[] declaredMethods = clazz.getDeclaredMethods();
        Predicate<Method> isMainMethod = method -> MAIN.equals(method.getName())
                && Modifier.isStatic(method.getModifiers())
                && method.getParameterCount() == 1
                && method.getParameterTypes()[0] == String[].class;
        return Arrays.stream(declaredMethods)
                .anyMatch(isMainMethod);
    }
}
