package io.mkrzywanski.executor.domain.compilation.model;

import io.mkrzywanski.executor.domain.common.Language;

public class CodeWithLanguage {
    private Language language;
    private Code code;

    private CodeWithLanguage() {
    }

    public CodeWithLanguage(final Language language, final Code code) {
        this.language = language;
        this.code = code;
    }

    public Language getLanguage() {
        return language;
    }

    public Code getCode() {
        return code;
    }
}
