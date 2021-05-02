package io.mkrzywanski.executor.domain.execution;

import io.mkrzywanski.executor.core.compilation.CompiledClass;
import io.mkrzywanski.executor.core.execution.ExecutionException;
import io.mkrzywanski.executor.core.execution.Executor;
import io.mkrzywanski.executor.core.execution.Executors;
import io.mkrzywanski.executor.core.loading.ClassLoadingException;
import io.mkrzywanski.executor.core.loading.ClassLoadingService;
import io.mkrzywanski.executor.core.loading.LoadedClasses;
import io.mkrzywanski.executor.domain.compilation.model.CompiledClasses;
import io.mkrzywanski.executor.domain.execution.exception.ExecutionFailedException;
import io.mkrzywanski.executor.domain.execution.interceptor.ThreadInterceptor;
import io.mkrzywanski.executor.domain.execution.interceptor.ThreadOutputInterceptorAdapter;
import io.mkrzywanski.executor.domain.execution.model.ExecutionResult;

import java.util.Set;
import java.util.stream.Collectors;

public final class ExecutionService {

    private final ClassLoadingService classLoadingService;
    private final Executor executor;

    public ExecutionService(final ThreadInterceptor threadInterceptor) {
        this.classLoadingService = new ClassLoadingService();
        this.executor = Executors.defaultExecutor(new ThreadOutputInterceptorAdapter(threadInterceptor));
    }

    public ExecutionResult execute(final CompiledClasses compiledClasses) throws ExecutionFailedException {
        try {
            return doExecute(compiledClasses);
        } catch (final ExecutionException | ClassLoadingException e) {
            throw new ExecutionFailedException(e.getMessage());
        }
    }

    private ExecutionResult doExecute(final CompiledClasses compiledClasses) throws ExecutionException {
        final io.mkrzywanski.executor.core.compilation.CompiledClasses compiledClasses1 = convertToCore(compiledClasses);
        final LoadedClasses loadedClasses = classLoadingService.load(compiledClasses1);
        final io.mkrzywanski.executor.core.execution.ExecutionResult execute = executor.execute(loadedClasses);
        return new ExecutionResult(execute.getOutput());
    }

    private io.mkrzywanski.executor.core.compilation.CompiledClasses convertToCore(final CompiledClasses compiledClasses) {
        final Set<CompiledClass> resultCompiledClasses = compiledClasses.asSet()
                .stream()
                .map(compiledClass -> new CompiledClass(compiledClass.getName(), compiledClass.getBytes()))
                .collect(Collectors.toSet());
        return new io.mkrzywanski.executor.core.compilation.CompiledClasses(resultCompiledClasses);
    }
}
