package io.mkrzywanski.onlinecodeexecutor.language;

import io.mkrzywanski.onlinecodeexecutor.language.compilation.CodeCompiler;
import io.mkrzywanski.onlinecodeexecutor.language.compilation.CompiledClasses;
import io.mkrzywanski.onlinecodeexecutor.language.compilation.CompilationException;
import io.mkrzywanski.onlinecodeexecutor.language.execution.Executor;
import io.mkrzywanski.onlinecodeexecutor.language.loading.ClassLoadingService;
import io.mkrzywanski.onlinecodeexecutor.language.loading.LoadedClasses;
import io.mkrzywanski.onlinecodeexecutor.language.execution.ExecutionException;
import io.mkrzywanski.onlinecodeexecutor.language.execution.ExecutionResult;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class CodeRunner {

    private final CodeCompiler codeCompiler;
    private final ClassLoadingService classLoadingService;
    private final Executor executor;

    @Inject
    public CodeRunner(final CodeCompiler codeCompiler, final ClassLoadingService classLoadingService, final Executor executor) {
        this.codeCompiler = codeCompiler;
        this.classLoadingService = classLoadingService;
        this.executor = executor;
    }

    public ExecutionResult run(final Code code) throws CompilationException, ExecutionException {
        final CompiledClasses compiledClasses = codeCompiler.compile(code);
        final LoadedClasses loadedClasses = classLoadingService.load(compiledClasses);
        return executor.execute(loadedClasses);
    }
}
