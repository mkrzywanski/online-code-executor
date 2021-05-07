package io.mkrzywanski.executor.domain.compilation;

import io.mkrzywanski.executor.domain.common.Language;
import io.mkrzywanski.executor.domain.common.exception.LanguageNotSupportedException;

public interface Compilers {
    Compiler forLanguage(Language language) throws LanguageNotSupportedException;
}
