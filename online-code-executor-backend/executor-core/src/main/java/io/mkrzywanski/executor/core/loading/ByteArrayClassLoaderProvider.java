package io.mkrzywanski.executor.core.loading;

import java.util.Map;

class ByteArrayClassLoaderProvider implements ClassLoaderProvider {
    @Override
    public ClassLoader from(final Map<String, byte[]> bytes) {
        return new ByteArrayClassLoader(bytes);
    }
}
