package io.mkrzywanski.onlinecodeexecutor.language.execution;

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
