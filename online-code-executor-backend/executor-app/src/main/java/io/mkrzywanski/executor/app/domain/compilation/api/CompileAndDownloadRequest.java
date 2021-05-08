package io.mkrzywanski.executor.app.domain.compilation.api;

import io.mkrzywanski.executor.domain.common.Language;

public final class CompileAndDownloadRequest {

    private final Language language;
    private final String code;

    private CompileAndDownloadRequest() {
        this.language = null;
        this.code = null;
    }

    public CompileAndDownloadRequest(final Language language, final String code) {
        this.language = language;
        this.code = code;
    }

    public Language getLanguage() {
        return language;
    }

    public String getCode() {
        return code;
    }
}
