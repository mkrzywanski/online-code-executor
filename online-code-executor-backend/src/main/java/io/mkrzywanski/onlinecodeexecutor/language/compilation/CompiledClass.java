package io.mkrzywanski.onlinecodeexecutor.language.compilation;

import java.util.Arrays;
import java.util.Objects;

public class CompiledClass {
    public static final int PRIME = 31;
    private final String name;
    private final byte[] bytes;

    public CompiledClass(final String name, final byte[] bytes) {
        this.name = name;
        this.bytes = Arrays.copyOf(bytes, bytes.length);
    }

    public String getName() {
        return name;
    }

    public byte[] getBytes() {
        return Arrays.copyOf(bytes, bytes.length);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CompiledClass)) {
            return false;
        }
        final CompiledClass that = (CompiledClass) o;
        return getName().equals(that.getName()) &&
                Arrays.equals(getBytes(), that.getBytes());
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(getName());
        result = PRIME * result + Arrays.hashCode(getBytes());
        return result;
    }
}
