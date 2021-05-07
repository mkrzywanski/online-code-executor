package io.mkrzywanski.executor.app.config;

import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Factory;
import io.micronaut.context.annotation.Value;
import io.mkrzywanski.executor.app.domain.execution.PrintStreamProxy;
import io.mkrzywanski.executor.domain.compilation.CompilationService;
import io.mkrzywanski.executor.domain.compilation.CompiledCodeCompressionService;
import io.mkrzywanski.executor.domain.compilation.Compilers;
import io.mkrzywanski.executor.domain.compilation.DefaultCompilers;
import io.mkrzywanski.executor.domain.execution.ExecutionService;
import io.mkrzywanski.executor.domain.execution.interceptor.DefaultThreadInterceptor;

import javax.inject.Singleton;
import java.io.PrintStream;
import java.nio.file.Path;

@Factory
public class ApplicationBeanFactory {

    @Value("${groovy.compiler.directory}")
    private Path groovyBaseDir;

    @Value("${kotlin.compiler.directory}")
    private Path kotlinBaseDir;

    @Singleton
    @Bean
    public Compilers compilers() {
        return DefaultCompilers.CompilerBuilder.newInstance()
                .withGroovyEnabled(groovyBaseDir)
                .withKotlinEnabled(kotlinBaseDir)
                .withJavaEnabled()
                .build();
    }

    @Bean
    @Singleton
    public PrintStream printStreamProxy(final DefaultThreadInterceptor defaultThreadInterceptor) {
        return PrintStreamProxy.create(defaultThreadInterceptor, System.out);
    }

    @Bean
    @Singleton
    public CompilationService compilationService(final Compilers compilers) {
        return new CompilationService(compilers);
    }

    @Bean
    @Singleton
    public ExecutionService executionService(final DefaultThreadInterceptor interceptor) {
        return new ExecutionService(interceptor);
    }

    @Bean
    @Singleton
    public DefaultThreadInterceptor defaultThreadInterceptor() {
        return new DefaultThreadInterceptor(System.out);
    }

    @Bean
    @Singleton
    public CompiledCodeCompressionService compiledCodeCompressionService() {
        return CompiledCodeCompressionService.newInstance();
    }
}
