package io.mkrzywanski.onlinecodeexecutor.config;

import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Factory;
import io.micronaut.context.annotation.Value;
import io.mkrzywanski.onlinecodeexecutor.language.interceptor.ThreadOutputInterceptor;
import io.mkrzywanski.onlinecodeexecutor.language.interceptor.ThreadOutputPrintStreamInterceptor;
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
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.InvocationHandlerAdapter;
import net.bytebuddy.matcher.ElementMatchers;

import javax.inject.Singleton;
import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Paths;
import java.util.EnumMap;
import java.util.Map;

@Factory
public class ApplicationBeanFactory {

    @Value("${groovy.compiler.directory}")
    private String groovyBaseDir;

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
    public ThreadOutputPrintStreamInterceptor dynamicInvocationHandler() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        ThreadOutputPrintStreamInterceptor callback = new ThreadOutputPrintStreamInterceptor(System.out);
        PrintStream proxy = new ByteBuddy()
                .subclass(PrintStream.class)
                .method(ElementMatchers.any())
                .intercept(InvocationHandlerAdapter.of(callback))
                .make()
                .load(ApplicationBeanFactory.class.getClassLoader())
                .getLoaded()
                .getDeclaredConstructor(OutputStream.class)
                .newInstance(System.out);
        System.setOut(proxy);
        return callback;
    }

    @Singleton
    @Bean
    public LanguageToolsResolver languageToolsResolver(final GroovyLanguageTools groovyLanguageTools, final JavaLanguageTools javaLanguageTools) {
        Map<Language, LanguageTools> languageToolsMap = new EnumMap<>(Language.class);
        languageToolsMap.put(Language.JAVA, javaLanguageTools);
        languageToolsMap.put(Language.GROOVY, groovyLanguageTools);
        return new LanguageToolsResolver(languageToolsMap);
    }

//    @Bean
//    @Singleton
//    public ThreadAwarePrintStream threadAwarePrintStream() {
//        ThreadAwarePrintStream threadAwarePrintStream = new ThreadAwarePrintStream(System.out);
//        System.setOut(threadAwarePrintStream);
//        return threadAwarePrintStream;
//    }
}
