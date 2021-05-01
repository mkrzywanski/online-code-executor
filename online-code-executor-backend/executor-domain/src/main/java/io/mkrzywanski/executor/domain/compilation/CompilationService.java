package io.mkrzywanski.executor.domain.compilation;

import io.mkrzywanski.executor.core.compilation.CompilationException;
import io.mkrzywanski.executor.core.compilation.CompiledClass;
import io.mkrzywanski.executor.core.compilation.Compiler;
import io.mkrzywanski.executor.domain.compilation.exception.CompilationFailedException;
import io.mkrzywanski.executor.domain.compilation.model.Code;
import io.mkrzywanski.executor.domain.compilation.model.CompilationResult;
import io.mkrzywanski.executor.domain.compilation.model.CompiledClasses;

import java.util.Set;
import java.util.stream.Collectors;

public class CompilationService {

    private final Compilers compilers;

    public CompilationService(final Compilers compilers) {
        this.compilers = compilers;
    }

    public CompilationResult compile(final Code code) throws CompilationFailedException {
        final Set<CompiledClass> compiledClasses = doCompile(code);
        return compilationResult(compiledClasses);
    }

    private Set<CompiledClass> doCompile(final Code code) throws CompilationFailedException {
        try {
            final Compiler compiler = compilers.forLanguage(code.getLanguage());
            return compiler.compile(code.getValue());
        } catch (final CompilationException e) {
            throw new CompilationFailedException(e.getReport());
        }
    }

    private CompilationResult compilationResult(final Set<CompiledClass> set) {
        final Set<io.mkrzywanski.executor.domain.compilation.model.CompiledClass> collect = set.stream()
                .map(compiledClass -> new io.mkrzywanski.executor.domain.compilation.model.CompiledClass(compiledClass.getName(), compiledClass.getBytes()))
                .collect(Collectors.toSet());
        return new CompilationResult(new CompiledClasses(collect));
    }
}
