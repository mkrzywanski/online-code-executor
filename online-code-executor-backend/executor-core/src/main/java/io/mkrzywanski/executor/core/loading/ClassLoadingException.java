package io.mkrzywanski.executor.core.loading;

public class ClassLoadingException extends RuntimeException {
    ClassLoadingException(final Throwable cause) {
        super(cause);
    }

    public ClassLoadingException(final String message) {
        super(message);
    }

    static ClassLoadingException noMainMethod() {
        return new ClassLoadingException("No main method found");
    }
}
