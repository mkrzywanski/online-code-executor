package io.mkrzywanski.executor.app;

import io.micronaut.runtime.Micronaut;

public class Application {

    public static void main(final String[] args) {
        Micronaut.build(args)
                .mainClass(Application.class)
                .start();
    }
}
