package io.mkrzywanski.onlinecodeexecutor.language.loading;

import io.mkrzywanski.onlinecodeexecutor.language.compilation.CompiledClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Singleton
public class ClassLoadingService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClassLoadingService.class);

    public Set<Class<?>> load(Set<CompiledClass> compiledClass) {

        Map<String, byte[]> classBytes = compiledClass.stream()
                .collect(Collectors.toMap(CompiledClass::getClassName, CompiledClass::getBytes));

        ClassLoader classLoader = new ByteArrayClassLoader(classBytes);

        return classBytes.keySet()
                .stream()
                .map(className -> load(classLoader, className))
                .collect(Collectors.toSet());
    }

    private Class<?> load(final ClassLoader classLoader, final String className) {
        try {
            return classLoader.loadClass(className);
        } catch (ClassNotFoundException e) {
            throw new ClassLoadingException(e);
        }
    }
}
