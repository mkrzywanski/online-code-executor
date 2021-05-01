package io.mkrzywanski.executor.domain.execution.model;

import java.util.Set;

public class ClassesToExecute {

    private final Set<ClassToExecute> classToExecuteSet;

    public ClassesToExecute(final Set<ClassToExecute> classToExecuteSet) {
        this.classToExecuteSet = classToExecuteSet;
    }

    public Set<ClassToExecute> asSet() {
        return classToExecuteSet;
    }
}
