package io.mkrzywanski.executor.core.compilation;

import java.util.UUID;

class CompilationId {

    private final UUID uuid;

    private CompilationId() {
        this.uuid = UUID.randomUUID();
    }

    static CompilationId generate() {
        return new CompilationId();
    }

    String asString() {
        return uuid.toString();
    }

    @Override
    public String toString() {
        return this.asString();
    }
}
