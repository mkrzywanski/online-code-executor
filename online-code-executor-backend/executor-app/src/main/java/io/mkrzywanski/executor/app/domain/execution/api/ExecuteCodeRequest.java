package io.mkrzywanski.executor.app.domain.execution.api;

import io.mkrzywanski.executor.domain.common.Language;

public final class ExecuteCodeRequest {

    private Language language;
    private String code;

    public ExecuteCodeRequest(final Language language, final String code) {
        this.language = language;
        this.code = code;
    }

    private ExecuteCodeRequest() {
    }

    public Language getLanguage() {
        return language;
    }

    public String getCode() {
        return code;
    }
}
