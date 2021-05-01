package io.mkrzywanski.executor.core.kotlin

import io.mkrzywanski.executor.core.compilation.KotlinCompiler
import io.mkrzywanski.executor.core.compilation.FileOperations
import spock.lang.Shared
import spock.lang.Specification

import java.nio.file.Files

import static org.hamcrest.Matchers.hasSize

class KotlinCompilerSpec extends Specification {
    @Shared
    def tempDir = Files.createTempDirectory("groovy")

    @Shared
    def fileOperations = FileOperations.create()
    def kotlinCompiler = new KotlinCompiler(tempDir, fileOperations)

    void cleanupSpec() {
        fileOperations.deleteDir(tempDir)
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
