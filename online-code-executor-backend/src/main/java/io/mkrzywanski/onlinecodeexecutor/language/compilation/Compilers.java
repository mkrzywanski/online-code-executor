package io.mkrzywanski.onlinecodeexecutor.language.compilation;

import io.mkrzywanski.onlinecodeexecutor.language.Language;
import io.mkrzywanski.onlinecodeexecutor.language.LanguageNotSupportedException;

import java.util.Map;
import java.util.Optional;

public class Compilers {
    private final Map<Language, Compiler> compilers;

    public Compilers(Map<Language, Compiler> compilers) {
        this.compilers = compilers;
    }

    public Compiler forLanguage(final Language language) {
        return Optional.ofNullable(compilers.get(language))
                .orElseThrow(() -> new LanguageNotSupportedException("Language not supported"));
    }
}
