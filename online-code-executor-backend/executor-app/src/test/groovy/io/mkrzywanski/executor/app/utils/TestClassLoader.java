package io.mkrzywanski.executor.app.utils;

import java.util.List;

public class TestClassLoader extends ClassLoader {

    private final List<PotentialClass> classBytes;

    public TestClassLoader(final List<PotentialClass> classBytes) {
        this.classBytes = classBytes;
    }

    @Override
    public Class<?> findClass(final String className) {
        final PotentialClass potentialClass1 = classBytes.stream().
                filter(potentialClass -> potentialClass.getName().equals(className))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Potential class should have been loaded"));
        return defineClass(potentialClass1.getName(), potentialClass1.getBytes(), 0, potentialClass1.getBytes().length);
    }
}
