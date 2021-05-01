package io.mkrzywanski.executor.domain.compilation;

import io.mkrzywanski.executor.core.compilation.Compiler;
import io.mkrzywanski.executor.domain.common.Language;

public interface Compilers {
    Compiler forLanguage(Language language);
}
