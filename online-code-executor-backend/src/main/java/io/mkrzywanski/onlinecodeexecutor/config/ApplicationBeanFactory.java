package io.mkrzywanski.onlinecodeexecutor.config;

import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Factory;
import io.micronaut.context.annotation.Value;
import io.mkrzywanski.onlinecodeexecutor.language.LanguageTools;
import io.mkrzywanski.onlinecodeexecutor.language.LanguageToolsResolver;
import io.mkrzywanski.onlinecodeexecutor.language.ThreadAwarePrintStream;
import io.mkrzywanski.onlinecodeexecutor.language.groovy.GroovyCompiler;
import io.mkrzywanski.onlinecodeexecutor.language.groovy.GroovyExecutor;
import io.mkrzywanski.onlinecodeexecutor.language.groovy.GroovyLanguageTools;
import io.mkrzywanski.onlinecodeexecutor.language.java.compiler.JavaCompiler;
import io.mkrzywanski.onlinecodeexecutor.language.java.JavaExecutor;
import io.mkrzywanski.onlinecodeexecutor.language.java.JavaLanguageTools;
import io.mkrzywanski.onlinecodeexecutor.language.Language;

import javax.inject.Singleton;
import java.nio.file.Paths;
import java.util.EnumMap;
import java.util.Map;

@Factory
public class ApplicationBeanFactory {

    @Value("${groovy.compiler.directory}")
    private String groovyBaseDir;

    @Singleton
    @Bean
    public LanguageToolsResolver languageToolsResolver() {
        Map<Language, LanguageTools> languageToolsMap = new EnumMap<>(Language.class);
        languageToolsMap.put(Language.JAVA, new JavaLanguageTools(new JavaCompiler(), new JavaExecutor(threadAwarePrintStream())));
        languageToolsMap.put(Language.GROOVY, new GroovyLanguageTools(new GroovyExecutor(threadAwarePrintStream()), new GroovyCompiler(Paths.get(groovyBaseDir))));
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
