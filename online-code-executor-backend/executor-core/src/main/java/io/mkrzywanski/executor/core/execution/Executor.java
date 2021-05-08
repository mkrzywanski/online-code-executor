package io.mkrzywanski.executor.core.execution;

import io.mkrzywanski.executor.core.loading.LoadedClasses;

public interface Executor {
    ExecutionResult execute(LoadedClasses loadedClasses) throws ExecutionException;
}
