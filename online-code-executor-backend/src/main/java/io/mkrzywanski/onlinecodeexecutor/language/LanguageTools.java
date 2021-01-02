package io.mkrzywanski.onlinecodeexecutor.language;

import io.mkrzywanski.onlinecodeexecutor.language.compilation.Compiler;
import io.mkrzywanski.onlinecodeexecutor.language.execution.Executor;

public interface LanguageTools {
    Compiler compiler();
    Executor executor();
}
