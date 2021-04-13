package io.mkrzywanski.onlinecodeexecutor.language.execution;

import java.util.UUID;

public class ExecutionId {

    private final UUID uuid;

    private ExecutionId() {
        this.uuid = UUID.randomUUID();
    }

    public static ExecutionId generate() {
        return new ExecutionId();
    }

    public String asString() {
        return uuid.toString();
    }

    @Override
    public String toString() {
        return this.asString();
    }
}
