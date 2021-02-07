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
    public GroovyLanguageTools groovyLanguageTools(final ThreadAwarePrintStream threadAwarePrintStream) {
        return new GroovyLanguageTools(new GroovyExecutor(threadAwarePrintStream), new GroovyCompiler(Paths.get(groovyBaseDir)));
    }

    @Bean
    @Singleton
    public JavaLanguageTools javaLanguageTools(final ThreadAwarePrintStream threadAwarePrintStream) {
        return new JavaLanguageTools(new JavaCompiler(), new JavaExecutor(threadAwarePrintStream));
    }

    @Singleton
    @Bean
    public LanguageToolsResolver languageToolsResolver(final GroovyLanguageTools groovyLanguageTools, final JavaLanguageTools javaLanguageTools) {
        Map<Language, LanguageTools> languageToolsMap = new EnumMap<>(Language.class);
        languageToolsMap.put(Language.JAVA, javaLanguageTools);
        languageToolsMap.put(Language.GROOVY, groovyLanguageTools);
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
