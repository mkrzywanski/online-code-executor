package io.mkrzywanski.executor.core.loading;

import io.mkrzywanski.executor.core.compilation.CompiledClass;
import io.mkrzywanski.executor.core.compilation.CompiledClasses;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public final class ClassLoadingService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClassLoadingService.class);

    private final ClassLoaderProvider classLoaderProvider;

    ClassLoadingService(final ClassLoaderProvider classLoaderProvider) {
        this.classLoaderProvider = classLoaderProvider;
    }

    public static ClassLoadingService newInstance() {
        return new ClassLoadingService(new ByteArrayClassLoaderProvider());
    }

    public LoadedClasses load(final CompiledClasses compiledClass) {

        final Map<String, byte[]> classesBytes = compiledClass.asSet()
                .stream()
                .collect(Collectors.toMap(CompiledClass::getName, CompiledClass::getBytes));

        final ClassLoader classLoader = classLoaderProvider.apply(classesBytes);

        final Set<String> classNames = classesBytes.keySet();
        final Set<Class<?>> classes = loadClasses(classNames, classLoader);

        return new LoadedClasses(classes);
    }

    @NotNull
    private Set<Class<?>> loadClasses(final Set<String> classNames, final ClassLoader classLoader) {
        return classNames
                .stream()
                .map(className -> loadInternal(classLoader, className))
                .collect(Collectors.toSet());
    }

    private Class<?> loadInternal(final ClassLoader classLoader, final String className) {
        try {
            return classLoader.loadClass(className);
        } catch (final ClassNotFoundException e) {
            throw new ClassLoadingException(e);
        }
    }
}
