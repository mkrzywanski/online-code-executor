package io.mkrzywanski.onlinecodeexecutor.language;

public class LanguageNotSupportedException extends RuntimeException {
    public LanguageNotSupportedException(final String message) {
        super(message);
    }
}
