package io.mkrzywanski.executor.core.execution;

public class ExecutionResult {

    private final String output;

    public ExecutionResult(final String output) {
        this.output = output;
    }

    public String getOutput() {
        return output;
    }
}
