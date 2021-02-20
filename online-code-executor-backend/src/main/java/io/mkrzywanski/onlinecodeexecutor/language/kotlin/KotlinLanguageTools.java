package io.mkrzywanski.onlinecodeexecutor.language.kotlin;

import io.mkrzywanski.onlinecodeexecutor.language.LanguageTools;
import io.mkrzywanski.onlinecodeexecutor.language.compilation.Compiler;
import io.mkrzywanski.onlinecodeexecutor.language.execution.Executor;

public class KotlinLanguageTools implements LanguageTools {

    private final KotlinCompiler kotlinCompiler;
    private final Executor executor;

    public KotlinLanguageTools(KotlinCompiler kotlinCompiler, Executor executor) {
        this.kotlinCompiler = kotlinCompiler;
        this.executor = executor;
    }

    @Override
    public Compiler compiler() {
        return kotlinCompiler;
    }

    @Override
    public Executor executor() {
        return executor;
    }
}
