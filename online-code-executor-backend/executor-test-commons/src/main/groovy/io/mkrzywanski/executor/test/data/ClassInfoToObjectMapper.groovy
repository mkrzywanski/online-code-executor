package io.mkrzywanski.executor.test.data;

import java.util.function.BiFunction;

@FunctionalInterface
interface ClassInfoToObjectMapper<T> extends BiFunction<String, byte[], T> {
}
