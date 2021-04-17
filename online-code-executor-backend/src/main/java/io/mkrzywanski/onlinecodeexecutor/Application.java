package io.mkrzywanski.onlinecodeexecutor;

import io.micronaut.runtime.Micronaut;

public class Application {

    public static void main(final String[] args) {
        Micronaut.build(args)
                .mainClass(Application.class)
                .start();
    }
}
