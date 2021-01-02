package io.mkrzywanski.onlinecodeexecutor.language.compilation;

import java.util.Arrays;
import java.util.Objects;

public class CompiledClass {
    private final String className;
    private final byte[] bytes;

    public CompiledClass(String className, byte[] bytes) {
        this.className = className;
        this.bytes = bytes;
    }

    public String getClassName() {
        return className;
    }

    public byte[] getBytes() {
        return bytes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CompiledClass)) return false;
        CompiledClass that = (CompiledClass) o;
        return getClassName().equals(that.getClassName()) &&
                Arrays.equals(getBytes(), that.getBytes());
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(getClassName());
        result = 31 * result + Arrays.hashCode(getBytes());
        return result;
    }
}
