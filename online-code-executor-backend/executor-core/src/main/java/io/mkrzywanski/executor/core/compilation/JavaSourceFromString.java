package io.mkrzywanski.executor.core.compilation;

import javax.tools.SimpleJavaFileObject;
import java.net.URI;

class JavaSourceFromString extends SimpleJavaFileObject {

    private final String code;

    JavaSourceFromString(final String name, final String code) {
        super(URI.create("string:///" + name.replace('.', '/') + Kind.SOURCE.extension),
                Kind.SOURCE);
        this.code = code;
    }

    @Override
    public CharSequence getCharContent(final boolean ignoreEncodingErrors) {
        return code;
    }
}
