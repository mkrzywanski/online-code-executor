package io.mkrzywanski.executor.core.compilation;

public class CompilationException extends Exception {

    private final String report;

    CompilationException(final String message) {
        super(message);
        this.report = "";
    }

    CompilationException(final String message, final String report) {
        super(message);
        this.report = report;
    }

    CompilationException(final Exception e) {
        super(e);
        this.report = "";
    }

    public String getReport() {
        return report;
    }
}
