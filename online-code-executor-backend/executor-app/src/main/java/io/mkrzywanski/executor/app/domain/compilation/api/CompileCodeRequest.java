package io.mkrzywanski.executor.app.domain.compilation.api;

import io.mkrzywanski.executor.domain.common.Language;

final class CompileCodeRequest {
    private final Language language;
    private final String code;

    CompileCodeRequest(final Language language, final String code) {
        this.language = language;
        this.code = code;
    }

    Language getLanguage() {
        return language;
    }

    String getCode() {
        return code;
    }
}
