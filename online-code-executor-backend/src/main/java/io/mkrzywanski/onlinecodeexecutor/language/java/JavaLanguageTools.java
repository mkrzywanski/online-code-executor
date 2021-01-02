package io.mkrzywanski.onlinecodeexecutor.language.java;

import io.mkrzywanski.onlinecodeexecutor.language.compilation.Compiler;
import io.mkrzywanski.onlinecodeexecutor.language.execution.Executor;
import io.mkrzywanski.onlinecodeexecutor.language.LanguageTools;
import io.mkrzywanski.onlinecodeexecutor.language.java.compiler.JavaCompiler;

public class JavaLanguageTools implements LanguageTools {
    private final JavaCompiler javaCompiler;
    private final JavaExecutor javaExecutor;

    public JavaLanguageTools(final JavaCompiler javaCompiler, final JavaExecutor javaExecutor) {
        this.javaCompiler = javaCompiler;
        this.javaExecutor = javaExecutor;
    }

    @Override
    public Compiler compiler() {
        return javaCompiler;
    }

    @Override
    public Executor executor() {
        return javaExecutor;
    }
}
