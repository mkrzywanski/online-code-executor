package io.mkrzywanski.onlinecodeexecutor.language;

public class Code {
    private Language language;
    private String value;

    private Code() {
    }

    public Code(Language language, String value) {
        this.language = language;
        this.value = value;
    }

    public Language getLanguage() {
        return language;
    }

    public String getValue() {
        return value;
    }
}
