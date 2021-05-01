package io.mkrzywanski.executor.domain.common;

public class LanguageNotSupportedException extends RuntimeException {
    public LanguageNotSupportedException(final String message) {
        super(message);
    }
}
