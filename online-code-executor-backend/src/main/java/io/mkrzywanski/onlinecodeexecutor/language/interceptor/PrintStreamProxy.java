package io.mkrzywanski.onlinecodeexecutor.language.interceptor;

import io.mkrzywanski.onlinecodeexecutor.infrastructure.config.ApplicationBeanFactory;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.InvocationHandlerAdapter;
import net.bytebuddy.matcher.ElementMatchers;
import org.jetbrains.annotations.NotNull;

import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;

public final class PrintStreamProxy {

    private PrintStreamProxy() {
    }

    public static PrintStream create(final InvocationHandler handler, final PrintStream target) {
        try {
            return doCreate(handler, target);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @NotNull
    private static PrintStream doCreate(final InvocationHandler handler, final PrintStream target) throws Exception {
        return new ByteBuddy()
                .subclass(PrintStream.class)
                .method(ElementMatchers.any())
                .intercept(InvocationHandlerAdapter.of(handler))
                .make()
                .load(ApplicationBeanFactory.class.getClassLoader())
                .getLoaded()
                .getDeclaredConstructor(OutputStream.class)
                .newInstance(target);
    }
}
