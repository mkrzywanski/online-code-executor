package io.mkrzywanski.onlinecodeexecutor.language.execution;

public class ExecutionResult {

    private String output;

    public ExecutionResult() {
    }

    public ExecutionResult(String output) {
        this.output = output;
    }

    public String getOutput() {
        return output;
    }
}
