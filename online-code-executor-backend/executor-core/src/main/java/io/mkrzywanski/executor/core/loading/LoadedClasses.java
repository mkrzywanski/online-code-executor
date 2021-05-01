package io.mkrzywanski.executor.core.loading;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

public class LoadedClasses {

    private static final String MAIN = "main";

    private final Set<Class<?>> classes;

    public LoadedClasses(final Set<Class<?>> classes) {
        this.classes = classes;
    }

    public Class<?> mainClass() throws ClassLoadingException {
        return findMainClass().orElseThrow(ClassLoadingException::noMainMethod);
    }

    private Optional<Class<?>> findMainClass() {
        return this.classes.stream()
                .filter(this::isMainClass)
                .findFirst();
    }

    private boolean isMainClass(final Class<?> clazz) {
        final Method[] declaredMethods = clazz.getDeclaredMethods();
        final Predicate<Method> isMainMethod = method -> MAIN.equals(method.getName())
                && Modifier.isStatic(method.getModifiers())
                && method.getParameterCount() == 1
                && method.getParameterTypes()[0] == String[].class;
        return Arrays.stream(declaredMethods)
                .anyMatch(isMainMethod);
    }
}
