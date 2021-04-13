package io.mkrzywanski.onlinecodeexecutor.language.execution;

public class ExecutionException extends Exception {
    public ExecutionException(final String message) {
        super(message);
    }

    public ExecutionException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public static ExecutionException noMainMethod() {
        return new ExecutionException("No main method found");
    }
}
