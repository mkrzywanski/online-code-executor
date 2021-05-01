package io.mkrzywanski.executor.app.infra.web.handler;

public class ErrorOutput {
    private final int httpStatus;
    private final String message;

    public ErrorOutput(final int httpStatus, final String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public int getHttpStatus() {
        return httpStatus;
    }

    public String getMessage() {
        return message;
    }
}
