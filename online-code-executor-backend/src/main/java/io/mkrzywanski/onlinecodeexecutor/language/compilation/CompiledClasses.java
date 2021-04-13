package io.mkrzywanski.onlinecodeexecutor.language.compilation;

import java.util.Set;

public class CompiledClasses {

    private final Set<CompiledClass> compiledClasses;

    public CompiledClasses(Set<CompiledClass> compiledClasses) {
        this.compiledClasses = compiledClasses;
    }

    public Set<CompiledClass> getCompiledClasses() {
        return compiledClasses;
    }
}
