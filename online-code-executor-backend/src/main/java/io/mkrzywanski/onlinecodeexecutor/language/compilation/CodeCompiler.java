package io.mkrzywanski.onlinecodeexecutor.language.compilation;

import io.mkrzywanski.onlinecodeexecutor.language.Code;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Set;

@Singleton
public class CodeCompiler {

    private final Compilers compilers;

    @Inject
    public CodeCompiler(Compilers compilers) {
        this.compilers = compilers;
    }

    public CompiledClasses compile(final Code code) throws CompilationException {
        Compiler compiler = compilers.forLanguage(code.getLanguage());
        Set<CompiledClass> compile = compiler.compile(code.getValue());
        return new CompiledClasses(compile);
    }
}
