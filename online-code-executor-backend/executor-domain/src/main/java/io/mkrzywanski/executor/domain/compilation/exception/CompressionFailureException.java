package io.mkrzywanski.executor.domain.compilation.exception;

public class CompressionFailureException extends Throwable {
    public CompressionFailureException(final Exception e) {
        super(e);
    }
}
