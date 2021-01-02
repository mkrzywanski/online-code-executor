package io.mkrzywanski.onlinecodeexecutor.config;

import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Factory;
import io.mkrzywanski.onlinecodeexecutor.language.LanguageTools;
import io.mkrzywanski.onlinecodeexecutor.language.LanguageToolsResolver;
import io.mkrzywanski.onlinecodeexecutor.language.ThreadAwarePrintStream;
import io.mkrzywanski.onlinecodeexecutor.language.java.compiler.JavaCompiler;
import io.mkrzywanski.onlinecodeexecutor.language.java.JavaExecutor;
import io.mkrzywanski.onlinecodeexecutor.language.java.JavaLanguageTools;
import io.mkrzywanski.onlinecodeexecutor.language.Language;

import javax.inject.Singleton;
import java.util.EnumMap;
import java.util.Map;

@Factory
public class ApplicationBeanFactory {

    @Singleton
    @Bean
    public LanguageToolsResolver languageToolsResolver() {
        Map<Language, LanguageTools> languageToolsMap = new EnumMap<>(Language.class);
        languageToolsMap.put(Language.JAVA, new JavaLanguageTools(new JavaCompiler(), new JavaExecutor(threadAwarePrintStream())));
        return new LanguageToolsResolver(languageToolsMap);
    }

    @Bean
    @Singleton
    public ThreadAwarePrintStream threadAwarePrintStream() {
        ThreadAwarePrintStream threadAwarePrintStream = new ThreadAwarePrintStream(System.out);
        System.setOut(threadAwarePrintStream);
        return threadAwarePrintStream;
    }
}
