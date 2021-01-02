package io.mkrzywanski.onlinecodeexecutor.language.execution;

import java.util.Set;

public interface Executor {
    String execute(Set<Class<?>> loadedClasses) throws ExecutionException;
}
