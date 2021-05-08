package io.mkrzywanski.executor.app.infra.web.handler;

public class ErrorResponse {
    private final int httpStatus;
    private final String message;

    public ErrorResponse(final int httpStatus, final String message) {
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
