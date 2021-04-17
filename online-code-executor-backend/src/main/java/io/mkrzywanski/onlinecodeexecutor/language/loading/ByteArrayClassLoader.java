package io.mkrzywanski.onlinecodeexecutor.language.loading;

import java.util.Map;

public class ByteArrayClassLoader extends ClassLoader {

    private final Map<String, byte[]> classes;

    public ByteArrayClassLoader(final Map<String, byte[]> classes) {
        this.classes = classes;
    }

    @Override
    public Class<?> findClass(final String className) {
        final byte[] bytes = classes.get(className);

        return defineClass(className, bytes, 0, bytes.length);
    }

}
