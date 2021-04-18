package io.mkrzywanski.onlinecodeexecutor.language.loading;

import io.mkrzywanski.onlinecodeexecutor.language.compilation.CompiledClass;
import io.mkrzywanski.onlinecodeexecutor.language.compilation.CompiledClasses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Singleton
public class ClassLoadingService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClassLoadingService.class);

    public LoadedClasses load(final CompiledClasses compiledClass) {

        final Map<String, byte[]> classBytes = compiledClass.getCompiledClasses()
                .stream()
                .collect(Collectors.toMap(CompiledClass::getName, CompiledClass::getBytes));

        final ClassLoader classLoader = new ByteArrayClassLoader(classBytes);

        final Set<Class<?>> classes = classBytes.keySet()
                .stream()
                .map(className -> load(classLoader, className))
                .collect(Collectors.toSet());

        return new LoadedClasses(classes);
    }

    private Class<?> load(final ClassLoader classLoader, final String className) {
        try {
            return classLoader.loadClass(className);
        } catch (final ClassNotFoundException e) {
            throw new ClassLoadingException(e);
        }
    }
}
