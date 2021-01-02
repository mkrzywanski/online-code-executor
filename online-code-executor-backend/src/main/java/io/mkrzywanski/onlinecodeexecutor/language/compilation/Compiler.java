package io.mkrzywanski.onlinecodeexecutor.language.compilation;

import java.util.Set;

public interface Compiler {
    Set<CompiledClass> compile(String code) throws CompilationException;
}
