package io.mkrzywanski.executor.core.execution;

public class ExecutionException extends Exception {

    ExecutionException(final String message, final Throwable cause) {
        super(message, cause);
    }

}
