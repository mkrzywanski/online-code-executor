package io.mkrzywanski.onlinecodeexecutor.language.compilation;

public class CompilationException extends Exception {
    private final String report;

    public CompilationException(String message) {
        super(message);
        this.report = "";
    }

    public CompilationException(String message, String report) {
        super(message);
        this.report = report;
    }

    public String getReport() {
        return report;
    }
}
