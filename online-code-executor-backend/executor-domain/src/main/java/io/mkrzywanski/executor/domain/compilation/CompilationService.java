package io.mkrzywanski.executor.domain.compilation;

import io.mkrzywanski.executor.domain.compilation.exception.CompilationFailedException;
import io.mkrzywanski.executor.domain.compilation.model.Code;
import io.mkrzywanski.executor.domain.compilation.model.CompilationResult;
import io.mkrzywanski.executor.domain.compilation.model.CompiledClass;
import io.mkrzywanski.executor.domain.compilation.model.CompiledClasses;

import java.util.Set;

public class CompilationService {

    private final Compilers compilers;

    public CompilationService(final Compilers compilers) {
        this.compilers = compilers;
    }

    public CompilationResult compile(final Code code) throws CompilationFailedException {
        final Set<CompiledClass> compiledClasses = doCompile(code);
        return new CompilationResult(new CompiledClasses(compiledClasses));
    }

    private Set<CompiledClass> doCompile(final Code code) throws CompilationFailedException {
        final Compiler compiler = compilers.forLanguage(code.getLanguage());
        return compiler.compile(code.asString());
    }
}
