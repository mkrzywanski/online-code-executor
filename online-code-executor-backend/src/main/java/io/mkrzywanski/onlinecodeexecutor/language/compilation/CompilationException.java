package io.mkrzywanski.onlinecodeexecutor.language.compilation;

public class CompilationException extends Exception {
    private final String report;

    public CompilationException(final String message) {
        super(message);
        this.report = "";
    }

    public CompilationException(final String message, final String report) {
        super(message);
        this.report = report;
    }

    public CompilationException(final Exception e) {
        super(e);
        this.report = "";
    }

    public String getReport() {
        return report;
    }
}
