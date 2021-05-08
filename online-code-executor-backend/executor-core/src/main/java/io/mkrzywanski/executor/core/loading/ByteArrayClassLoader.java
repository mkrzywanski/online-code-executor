package io.mkrzywanski.executor.core.loading;

import java.util.Map;

class ByteArrayClassLoader extends ClassLoader {

    private final Map<String, byte[]> classes;

    ByteArrayClassLoader(final Map<String, byte[]> classes) {
        this.classes = classes;
    }

    @Override
    public Class<?> findClass(final String className) {
        final byte[] bytes = classes.get(className);

        return defineClass(className, bytes, 0, bytes.length);
    }

}
