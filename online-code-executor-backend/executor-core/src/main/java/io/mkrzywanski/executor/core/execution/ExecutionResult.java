package io.mkrzywanski.executor.core.execution;

public class ExecutionResult {

    private String output;

    private ExecutionResult() {
    }

    public ExecutionResult(final String output) {
        this.output = output;
    }

    public String getOutput() {
        return output;
    }
}
