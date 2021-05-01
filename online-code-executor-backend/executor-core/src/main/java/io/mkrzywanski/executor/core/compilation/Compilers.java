package io.mkrzywanski.executor.core.compilation;

import java.nio.file.Path;

public class Compilers {

    private static final FileOperations FILE_OPERATIONS = FileOperations.create();

    public static Compiler java() {
        return new JavaCompiler();
    }

    public static Compiler groovy(final Path compilerPath) {
        return new GroovyCompiler(compilerPath, FILE_OPERATIONS);
    }

    public static Compiler kotlin(final Path compilerPath) {
        return new KotlinCompiler(compilerPath, FILE_OPERATIONS);
    }
}
