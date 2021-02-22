package io.mkrzywanski.onlinecodeexecutor.config;

import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Factory;
import io.micronaut.context.annotation.Value;
import io.mkrzywanski.onlinecodeexecutor.language.Language;
import io.mkrzywanski.onlinecodeexecutor.language.compilation.Compiler;
import io.mkrzywanski.onlinecodeexecutor.language.compilation.Compilers;
import io.mkrzywanski.onlinecodeexecutor.language.execution.Executor;
import io.mkrzywanski.onlinecodeexecutor.language.groovy.GroovyCompiler;
import io.mkrzywanski.onlinecodeexecutor.language.interceptor.PrintStreamProxy;
import io.mkrzywanski.onlinecodeexecutor.language.interceptor.ThreadOutputPrintStreamInterceptor;
import io.mkrzywanski.onlinecodeexecutor.language.execution.DefaultExecutor;
import io.mkrzywanski.onlinecodeexecutor.language.java.compiler.JavaCompiler;
import io.mkrzywanski.onlinecodeexecutor.language.kotlin.KotlinCompiler;

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
    public Compilers compilers() {
        Map<Language, Compiler> compilerMap = new EnumMap<>(Language.class);
        compilerMap.put(Language.JAVA, new JavaCompiler());
        compilerMap.put(Language.KOTLIN, new KotlinCompiler(Paths.get(kotlinBaseDir)));
        compilerMap.put(Language.GROOVY, new GroovyCompiler(Paths.get(groovyBaseDir)));
        return new Compilers(compilerMap);
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

    @Bean
    @Singleton
    public Executor executor(final ThreadOutputPrintStreamInterceptor interceptor) {
        return new DefaultExecutor(interceptor);
    }
}
