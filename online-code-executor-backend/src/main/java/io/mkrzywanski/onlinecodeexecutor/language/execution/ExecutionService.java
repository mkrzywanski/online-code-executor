package io.mkrzywanski.onlinecodeexecutor.language.execution;

import io.mkrzywanski.onlinecodeexecutor.language.Code;
import io.mkrzywanski.onlinecodeexecutor.language.LanguageNotSupportedException;
import io.mkrzywanski.onlinecodeexecutor.language.LanguageTools;
import io.mkrzywanski.onlinecodeexecutor.language.LanguageToolsResolver;
import io.mkrzywanski.onlinecodeexecutor.language.loading.ClassLoadingService;
import io.mkrzywanski.onlinecodeexecutor.language.compilation.CompilationException;
import io.mkrzywanski.onlinecodeexecutor.language.compilation.CompiledClass;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Set;

@Singleton
public class ExecutionService {

    private final LanguageToolsResolver languageToolsResolver;
    private final ClassLoadingService classLoadingService;

    @Inject
    public ExecutionService(final LanguageToolsResolver languageToolsResolver, final ClassLoadingService classLoadingService) {
        this.languageToolsResolver = languageToolsResolver;
        this.classLoadingService = classLoadingService;
    }

    public ExecutionResult execute(final Code code) throws CompilationException, ExecutionException {
        LanguageTools languageTools = languageToolsResolver.resolve(code.getLanguage())
                .orElseThrow(() -> new LanguageNotSupportedException("Language not found"));

        Set<CompiledClass> compiledClasses = languageTools.compiler().compile(code.getValue());
        Set<Class<?>> loadedClasses = classLoadingService.load(compiledClasses);
        String consoleOutput = languageTools.executor().execute(loadedClasses);
        return new ExecutionResult(consoleOutput);
    }
}
