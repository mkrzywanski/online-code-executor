package io.mkrzywanski.onlinecodeexecutor.language.execution;

public class ExecutionException extends Exception {
    public ExecutionException(String message) {
        super(message);
    }

    public ExecutionException(String message, Throwable cause) {
        super(message, cause);
    }

    public static ExecutionException noMainMethod() {
        return new ExecutionException("No main method found");
    }
}
