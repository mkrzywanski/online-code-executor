package io.mkrzywanski.executor.core.data;

import io.mkrzywanski.executor.core.compilation.CompiledClass;
import io.mkrzywanski.executor.core.compilation.CompiledClasses;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Set;

public class CompiledClassesObjectMother {
    public static CompiledClasses helloWorld() {
        final Class<?> c = HelloWorld.class;
        final String className = c.getName();
        final String classAsPath = className.replace('.', '/') + ".class";
        final InputStream stream = c.getClassLoader().getResourceAsStream(classAsPath);
        try {
            final byte[] bytes = Objects.requireNonNull(stream).readAllBytes();
            return new CompiledClasses(Set.of(new CompiledClass(HelloWorld.class.getName(), bytes)));
        } catch (final IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
