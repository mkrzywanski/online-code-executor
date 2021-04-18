package io.mkrzywanski.onlinecodeexecutor.language.compilation;

import io.mkrzywanski.onlinecodeexecutor.language.Code;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Set;

@Singleton
public class CodeCompiler {

    private final Compilers compilers;

    @Inject
    public CodeCompiler(final Compilers compilers) {
        this.compilers = compilers;
    }

    public CompiledClasses compile(final Code code) throws CompilationException {
        final Compiler compiler = compilers.forLanguage(code.getLanguage());
        final Set<CompiledClass> compile = compiler.compile(code.getValue());
        return new CompiledClasses(compile);
    }
}
