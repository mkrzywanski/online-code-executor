package io.mkrzywanski.executor.domain.common.exception;

public class LanguageNotSupportedException extends RuntimeException {
    public LanguageNotSupportedException(final String message) {
        super(message);
    }
}
