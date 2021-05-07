package io.mkrzywanski.executor.domain.compilation.model;

import io.mkrzywanski.executor.domain.common.Language;

public class Code {

    private Language language;
    private String value;

    private Code() {
    }

    public Code(final Language language, final String value) {
        this.language = language;
        this.value = value;
    }

    public Language getLanguage() {
        return language;
    }

    public String asString() {
        return value;
    }
}
