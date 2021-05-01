package io.mkrzywanski.executor.app.domain.execution.api;

public final class ExecuteCodeResponse {

    private String result;

    public ExecuteCodeResponse(final String result) {
        this.result = result;
    }

    private ExecuteCodeResponse() {
    }

    public String getResult() {
        return result;
    }
}
