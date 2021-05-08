package io.mkrzywanski.executor.core.compilation;

import java.util.Set;

public interface Compiler {
    Set<CompiledClass> compile(String code) throws CompilationException;
}
