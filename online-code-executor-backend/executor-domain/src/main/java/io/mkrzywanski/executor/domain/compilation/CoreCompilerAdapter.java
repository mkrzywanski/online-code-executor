package io.mkrzywanski.executor.domain.compilation;

import io.mkrzywanski.executor.core.compilation.CompilationException;
import io.mkrzywanski.executor.domain.compilation.exception.CompilationFailedException;
import io.mkrzywanski.executor.domain.compilation.model.CompiledClass;

import java.util.Set;
import java.util.stream.Collectors;

class CoreCompilerAdapter implements Compiler {

    private final io.mkrzywanski.executor.core.compilation.Compiler delegate;

    CoreCompilerAdapter(final io.mkrzywanski.executor.core.compilation.Compiler delegate) {
        this.delegate = delegate;
    }

    @Override
    public Set<CompiledClass> compile(final String code) throws CompilationFailedException {
        try {
            return toDomain(delegate.compile(code));
        } catch (final CompilationException e) {
            throw new CompilationFailedException(e);
        }
    }

    private Set<CompiledClass> toDomain(final Set<io.mkrzywanski.executor.core.compilation.CompiledClass> set) {
        return set.stream()
                .map(compiledClass -> new io.mkrzywanski.executor.domain.compilation.model.CompiledClass(compiledClass.getName(), compiledClass.getBytes()))
                .collect(Collectors.toSet());
    }
}
