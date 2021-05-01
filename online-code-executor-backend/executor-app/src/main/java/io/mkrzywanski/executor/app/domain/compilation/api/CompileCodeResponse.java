package io.mkrzywanski.executor.app.domain.compilation.api;

final class CompileCodeResponse {
    private final String output;

    CompileCodeResponse(final String output) {
        this.output = output;
    }

    String getOutput() {
        return output;
    }
}
