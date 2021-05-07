package io.mkrzywanski.executor.domain.compilation;

import io.mkrzywanski.executor.domain.compilation.exception.CompilationFailedException;
import io.mkrzywanski.executor.domain.compilation.model.CompiledClass;

import java.util.Set;

public interface Compiler {
    Set<CompiledClass> compile(String code) throws CompilationFailedException;
}
