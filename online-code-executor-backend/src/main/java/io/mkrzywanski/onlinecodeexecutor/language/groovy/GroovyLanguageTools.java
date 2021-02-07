package io.mkrzywanski.onlinecodeexecutor.language.groovy;

import io.mkrzywanski.onlinecodeexecutor.language.LanguageTools;
import io.mkrzywanski.onlinecodeexecutor.language.compilation.Compiler;
import io.mkrzywanski.onlinecodeexecutor.language.execution.Executor;

public class GroovyLanguageTools implements LanguageTools {

    private final GroovyExecutor groovyExecutor;
    private final GroovyCompiler groovyCompiler;

    public GroovyLanguageTools(GroovyExecutor groovyExecutor, GroovyCompiler groovyCompiler) {
        this.groovyExecutor = groovyExecutor;
        this.groovyCompiler = groovyCompiler;
    }

    @Override
    public Compiler compiler() {
        return groovyCompiler;
    }

    @Override
    public Executor executor() {
        return groovyExecutor;
    }
}
