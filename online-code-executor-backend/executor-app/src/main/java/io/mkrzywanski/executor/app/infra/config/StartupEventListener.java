package io.mkrzywanski.executor.app.infra.config;

import io.micronaut.context.event.StartupEvent;
import io.micronaut.runtime.event.annotation.EventListener;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.PrintStream;

@Singleton
public class StartupEventListener {

    private final PrintStream printStreamProxy;

    @Inject
    public StartupEventListener(final PrintStream printStreamProxy) {
        this.printStreamProxy = printStreamProxy;
    }

    @EventListener
    public void onStartupEvent(final StartupEvent event) {
        System.setOut(printStreamProxy);
    }
}
