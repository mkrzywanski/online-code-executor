package io.mkrzywanski.executor.domain.compilation.model;

public class CompilationResult {
    private final CompiledClasses compiledClasses;

    public CompilationResult(final CompiledClasses compiledClasses) {
        this.compiledClasses = compiledClasses;
    }

    public CompiledClasses getCompiledClasses() {
        return compiledClasses;
    }
}
