package io.mkrzywanski.onlinecodeexecutor.language.compilation;

import java.util.Arrays;
import java.util.Objects;

public class CompiledClass {
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CompiledClass)) return false;
        CompiledClass that = (CompiledClass) o;
        return getName().equals(that.getName()) &&
                Arrays.equals(getBytes(), that.getBytes());
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(getName());
        result = 31 * result + Arrays.hashCode(getBytes());
        return result;
    }
}
