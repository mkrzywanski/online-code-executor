package io.mkrzywanski.executor.core.execution;

public class ExecutionException extends Exception {
    ExecutionException(final String message) {
        super(message);
    }

    ExecutionException(final String message, final Throwable cause) {
        super(message, cause);
    }

    static ExecutionException noMainMethod() {
        return new ExecutionException("No main method found");
    }
}
