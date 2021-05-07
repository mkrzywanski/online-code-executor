package io.mkrzywanski.executor.domain.compilation;

import io.mkrzywanski.executor.core.compilation.Compiler;
import io.mkrzywanski.executor.domain.common.Language;
import io.mkrzywanski.executor.domain.common.exception.LanguageNotSupportedException;

import java.nio.file.Path;
import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;

public class DefaultCompilers implements Compilers {

    private final Map<Language, Compiler> compilerLanguageMap;

    DefaultCompilers(final Map<Language, Compiler> compilerMap) {
        this.compilerLanguageMap = compilerMap;
    }

    @Override
    public io.mkrzywanski.executor.domain.compilation.Compiler forLanguage(final Language language) {
        final Compiler compiler = Optional.ofNullable(compilerLanguageMap.get(language))
                .orElseThrow(() -> new LanguageNotSupportedException(language + " is not supported"));
        return new CoreCompilerAdapter(compiler);
    }

    public static class CompilerBuilder {

        private final Map<Language, Compiler> compilers = new EnumMap<>(Language.class);

        private CompilerBuilder() {
        }

        public static CompilerBuilder newInstance() {
            return new CompilerBuilder();
        }

        public CompilerBuilder withKotlinEnabled(final Path kotlinCompilerPath) {
            this.compilers.put(Language.KOTLIN, io.mkrzywanski.executor.core.compilation.Compilers.kotlin(kotlinCompilerPath));
            return this;
        }

        public CompilerBuilder withGroovyEnabled(final Path path) {
            this.compilers.put(Language.GROOVY, io.mkrzywanski.executor.core.compilation.Compilers.groovy(path));
            return this;
        }


        public CompilerBuilder withJavaEnabled() {
            this.compilers.put(Language.JAVA, io.mkrzywanski.executor.core.compilation.Compilers.java());
            return this;
        }

        public Compilers build() {
            return new DefaultCompilers(compilers);
        }
    }
}
