package io.mkrzywanski.executor.domain.execution.exception;

public class ExecutionFailedException extends Exception {
    public ExecutionFailedException(final String message) {
        super(message);
    }
}
