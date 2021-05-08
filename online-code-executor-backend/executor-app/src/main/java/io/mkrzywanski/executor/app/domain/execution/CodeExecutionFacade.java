package io.mkrzywanski.executor.app.domain.execution;

import io.mkrzywanski.executor.app.domain.execution.api.ExecuteCodeRequest;
import io.mkrzywanski.executor.app.domain.execution.api.ExecuteCodeResponse;
import io.mkrzywanski.executor.domain.compilation.model.Code;
import io.mkrzywanski.executor.domain.compilation.exception.CompilationFailedException;
import io.mkrzywanski.executor.domain.compilation.model.CompilationResult;
import io.mkrzywanski.executor.domain.compilation.CompilationService;
import io.mkrzywanski.executor.domain.execution.exception.ExecutionFailedException;
import io.mkrzywanski.executor.domain.execution.model.ExecutionResult;
import io.mkrzywanski.executor.domain.execution.ExecutionService;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
final class CodeExecutionFacade {

    private final CompilationService compilationService;
    private final ExecutionService executionService;

    @Inject
    CodeExecutionFacade(final CompilationService compilationService, final ExecutionService executionService) {
        this.compilationService = compilationService;
        this.executionService = executionService;
    }

    ExecuteCodeResponse executeCode(final ExecuteCodeRequest request) throws CompilationFailedException, ExecutionFailedException {
        final Code code = new Code(request.getLanguage(), request.getCode());
        final CompilationResult compilationResult = compile(code);
        final ExecutionResult execute = executionService.execute(compilationResult.getCompiledClasses());
        return new ExecuteCodeResponse(execute.getOutput());
    }

    private CompilationResult compile(final Code code) throws CompilationFailedException {
        try {
            return compilationService.compile(code);
        } catch (final CompilationFailedException e) {
            throw new CompilationFailedException(e.getReport());
        }
    }
}
