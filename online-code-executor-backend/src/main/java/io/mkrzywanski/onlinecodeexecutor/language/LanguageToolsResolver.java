package io.mkrzywanski.onlinecodeexecutor.language;

import java.util.Map;
import java.util.Optional;

public class LanguageToolsResolver {

    private final Map<Language, LanguageTools> languageToolsMap;

    public LanguageToolsResolver(final Map<Language, LanguageTools> languageToolsMap) {
        this.languageToolsMap = languageToolsMap;
    }

    public Optional<LanguageTools> resolve(final Language language) {
        return Optional.ofNullable(languageToolsMap.get(language));
    }
}
