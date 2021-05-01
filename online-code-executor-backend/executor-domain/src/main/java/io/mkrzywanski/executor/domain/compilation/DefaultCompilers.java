package io.mkrzywanski.executor.domain.compilation;

import io.mkrzywanski.executor.core.compilation.Compiler;
import io.mkrzywanski.executor.domain.common.Language;

import java.nio.file.Path;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class DefaultCompilers implements Compilers {

    private final Map<Language, Compiler> compilerLanguageMap;

    DefaultCompilers(final Path kotlinCompilerPath, final Path groovyCompilerPath) {
        Objects.requireNonNull(kotlinCompilerPath);
        Objects.requireNonNull(groovyCompilerPath);
        compilerLanguageMap = Map.of(
                Language.JAVA, io.mkrzywanski.executor.core.compilation.Compilers.java(),
                Language.GROOVY, io.mkrzywanski.executor.core.compilation.Compilers.groovy(groovyCompilerPath),
                Language.KOTLIN, io.mkrzywanski.executor.core.compilation.Compilers.kotlin(kotlinCompilerPath)
        );
    }

    @Override
    public Compiler forLanguage(final Language language) {
        return Optional.ofNullable(compilerLanguageMap.get(language)).orElseThrow(RuntimeException::new);
    }

    public static class CompilerBuilder {

        private Path kotlinPath;
        private Path groovyPath;

        public CompilerBuilder() {
        }

        public static CompilerBuilder newInstance() {
            return new CompilerBuilder();
        }

        public CompilerBuilder withKotlinPath(final Path path) {
            this.kotlinPath = path;
            return this;
        }

        public CompilerBuilder withGroovyPath(final Path path) {
            this.groovyPath = path;
            return this;
        }

        public Compilers build() {
            return new DefaultCompilers(kotlinPath, groovyPath);
        }
    }
}
