package io.mkrzywanski.executor.domain.execution.model;

public class ExecutionResult {
    private final String output;

    public ExecutionResult(final String output) {
        this.output = output;
    }

    public String getOutput() {
        return output;
    }
}
