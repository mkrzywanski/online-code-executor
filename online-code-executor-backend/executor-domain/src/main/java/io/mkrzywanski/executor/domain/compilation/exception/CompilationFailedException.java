package io.mkrzywanski.executor.domain.compilation.exception;

public class CompilationFailedException extends Exception {
    private final String report;

    public CompilationFailedException(final String report) {
        super("Compilation failed");
        this.report = report;
    }

    public CompilationFailedException(final Exception e) {
        super(e);
        report = "";
    }

    public String getReport() {
        return report;
    }
}
