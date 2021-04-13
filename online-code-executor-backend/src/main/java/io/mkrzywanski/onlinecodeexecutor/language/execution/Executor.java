package io.mkrzywanski.onlinecodeexecutor.language.execution;

import io.mkrzywanski.onlinecodeexecutor.language.loading.LoadedClasses;

public interface Executor {
    ExecutionResult execute(LoadedClasses loadedClasses) throws ExecutionException;
}
