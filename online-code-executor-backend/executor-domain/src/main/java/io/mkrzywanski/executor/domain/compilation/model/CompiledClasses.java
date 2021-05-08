package io.mkrzywanski.executor.domain.compilation.model;


import java.util.Set;

public class CompiledClasses {

    private final Set<CompiledClass> compiledClasses;

    public CompiledClasses(final Set<CompiledClass> compiledClasses) {
        this.compiledClasses = compiledClasses;
    }

    public Set<CompiledClass> asSet() {
        return compiledClasses;
    }
}
