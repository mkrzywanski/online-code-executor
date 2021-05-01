package io.mkrzywanski.executor.app.infra.web;

public final class Endpoints {

    public static final String VERSION = "/v1";
    public static final String EXECUTE = VERSION + "/execute";
    public static final String COMPILE_AND_COMPRESS = VERSION + "/sourceCode/compressed";

    private Endpoints() {
    }
}
