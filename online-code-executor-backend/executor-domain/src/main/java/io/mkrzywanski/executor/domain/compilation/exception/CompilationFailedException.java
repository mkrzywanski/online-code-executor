package io.mkrzywanski.executor.domain.compilation.exception;

import io.mkrzywanski.executor.domain.common.exception.BusinessException;

public class CompilationFailedException extends BusinessException {
    private final String report;

    public CompilationFailedException(final String report) {
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
