package io.mkrzywanski.onlinecodeexecutor.config;

import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Factory;
import io.micronaut.context.annotation.Value;
import io.mkrzywanski.onlinecodeexecutor.language.interceptor.PrintStreamProxy;
import io.mkrzywanski.onlinecodeexecutor.language.interceptor.ThreadOutputInterceptor;
import io.mkrzywanski.onlinecodeexecutor.language.interceptor.ThreadOutputPrintStreamInterceptor;
import io.mkrzywanski.onlinecodeexecutor.language.LanguageTools;
import io.mkrzywanski.onlinecodeexecutor.language.LanguageToolsResolver;
import io.mkrzywanski.onlinecodeexecutor.language.groovy.GroovyCompiler;
import io.mkrzywanski.onlinecodeexecutor.language.groovy.GroovyExecutor;
import io.mkrzywanski.onlinecodeexecutor.language.groovy.GroovyLanguageTools;
import io.mkrzywanski.onlinecodeexecutor.language.java.compiler.JavaCompiler;
import io.mkrzywanski.onlinecodeexecutor.language.java.JavaExecutor;
import io.mkrzywanski.onlinecodeexecutor.language.java.JavaLanguageTools;
import io.mkrzywanski.onlinecodeexecutor.language.Language;
import io.mkrzywanski.onlinecodeexecutor.language.kotlin.KotlinCompiler;
import io.mkrzywanski.onlinecodeexecutor.language.kotlin.KotlinLanguageTools;

import javax.inject.Singleton;
import java.io.PrintStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Paths;
import java.util.EnumMap;
import java.util.Map;

@Factory
public class ApplicationBeanFactory {

    @Value("${groovy.compiler.directory}")
    private String groovyBaseDir;

    @Value("${kotlin.compiler.directory}")
    private String kotlinBaseDir;

    @Singleton
    @Bean
    public KotlinLanguageTools kotlinLanguageTools(final ThreadOutputInterceptor threadOutputInterceptor) {
        return new KotlinLanguageTools(new KotlinCompiler(Paths.get(kotlinBaseDir)), new JavaExecutor(threadOutputInterceptor));
    }

    @Singleton
    @Bean
    public GroovyLanguageTools groovyLanguageTools(final ThreadOutputInterceptor interceptor) {
        return new GroovyLanguageTools(new GroovyExecutor(interceptor), new GroovyCompiler(Paths.get(groovyBaseDir)));
    }

    @Bean
    @Singleton
    public JavaLanguageTools javaLanguageTools(final ThreadOutputInterceptor interceptor) {
        return new JavaLanguageTools(new JavaCompiler(), new JavaExecutor(interceptor));
    }

    @Bean
    @Singleton
    public PrintStream printStreamProxy(final InvocationHandler invocationHandler) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        return PrintStreamProxy.create(invocationHandler, System.out);
    }

    @Bean
    @Singleton
    public ThreadOutputPrintStreamInterceptor dynamicInvocationHandler() {
        return new ThreadOutputPrintStreamInterceptor(System.out);
    }

    @Singleton
    @Bean
    public LanguageToolsResolver languageToolsResolver(final GroovyLanguageTools groovyLanguageTools,
                                                       final JavaLanguageTools javaLanguageTools,
                                                       final KotlinLanguageTools kotlinLanguageTools) {
        Map<Language, LanguageTools> languageToolsMap = new EnumMap<>(Language.class);
        languageToolsMap.put(Language.JAVA, javaLanguageTools);
        languageToolsMap.put(Language.GROOVY, groovyLanguageTools);
        languageToolsMap.put(Language.KOTLIN, kotlinLanguageTools);
        return new LanguageToolsResolver(languageToolsMap);
    }
}
