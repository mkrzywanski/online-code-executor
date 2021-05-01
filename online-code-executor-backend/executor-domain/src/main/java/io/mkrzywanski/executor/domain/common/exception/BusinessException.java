package io.mkrzywanski.executor.domain.common.exception;

public class BusinessException extends Exception {

    public BusinessException(final Exception e) {
        super(e);
    }

    public BusinessException() {

    }

}
