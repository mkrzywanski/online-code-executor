package io.mkrzywanski.onlinecodeexecutor.language.kotlin

import io.mkrzywanski.onlinecodeexecutor.language.compilation.CompilationException
import io.mkrzywanski.onlinecodeexecutor.utils.FileUtils
import spock.lang.Shared
import spock.lang.Specification

import java.nio.file.Files
import java.nio.file.Path

import static org.hamcrest.Matchers.hasSize

class KotlinCompilerSpec extends Specification {
    @Shared
    Path tempDir = Files.createTempDirectory("groovy");

    @Shared
    KotlinCompiler kotlinCompiler = new KotlinCompiler(tempDir)

    void cleanupSpec() {
        FileUtils.deleteDirectory(tempDir)
    }

    def "compile kotlin"() {
        given:
        String code = "fun main(args: Array<String>) {println(\"Hello, world!\")}"

        when:
        def compiledClasses = kotlinCompiler.compile(code)

        then:
        compiledClasses hasSize(1)

    }

    def "throw exception when compilation fails"() {
        given:
        String code = "fun main {println(\"Hello, world!\")}"

        when:
        def compile = kotlinCompiler.compile(code)

        then:
        def exception = thrown(CompilationException)
        exception.message == "Compilation failed"
        !exception.report.isEmpty()
    }
}
