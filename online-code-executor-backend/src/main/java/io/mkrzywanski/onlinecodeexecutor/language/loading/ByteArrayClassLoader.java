package io.mkrzywanski.onlinecodeexecutor.language.loading;

import java.util.Map;

public class ByteArrayClassLoader extends ClassLoader {

    private final Map<String, byte[]> classes;

    public ByteArrayClassLoader(Map<String, byte[]> classes) {
        this.classes = classes;
    }

    @Override
    public Class<?> findClass(final String className) {
        byte[] bytes = classes.get(className);

        return defineClass(className, bytes, 0, bytes.length);
    }

}
