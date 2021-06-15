package io.mkrzywanski.executor.app.utils;

public class PotentialClass {

    private final String name;
    private final byte[] bytes;

    public PotentialClass(final String name, final byte[] bytes) {
        this.name = name;
        this.bytes = bytes;
    }

    public String getName() {
        return name;
    }

    public byte[] getBytes() {
        return bytes;
    }
}
