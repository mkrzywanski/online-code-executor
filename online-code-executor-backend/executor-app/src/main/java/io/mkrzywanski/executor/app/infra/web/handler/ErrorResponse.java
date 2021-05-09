package io.mkrzywanski.executor.app.infra.web.handler;

public final class ErrorResponse {

    private int httpStatus;
    private String message;

    private ErrorResponse() {
    }

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
