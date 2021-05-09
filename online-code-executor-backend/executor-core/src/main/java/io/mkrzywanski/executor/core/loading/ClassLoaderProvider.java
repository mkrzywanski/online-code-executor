package io.mkrzywanski.executor.core.loading;

import java.util.Map;
import java.util.function.Function;

@FunctionalInterface
public interface ClassLoaderProvider extends Function<Map<String, byte[]>, ClassLoader> {

    ClassLoader from(Map<String, byte[]> bytes);

    @Override
    default ClassLoader apply(final Map<String, byte[]> stringMap) {
        return from(stringMap);
    }
}
