package io.mkrzywanski.onlinecodeexecutor.language.loading;

import io.mkrzywanski.onlinecodeexecutor.language.execution.ExecutionException;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

public class LoadedClasses {

    private static final String MAIN = "main";

    private final Set<Class<?>> classes;

    public LoadedClasses(Set<Class<?>> classes) {
        this.classes = classes;
    }

    public Class<?> mainClass() throws ExecutionException {
        return findMainClass().orElseThrow(ExecutionException::noMainMethod);
    }

    private Optional<Class<?>> findMainClass() {
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
