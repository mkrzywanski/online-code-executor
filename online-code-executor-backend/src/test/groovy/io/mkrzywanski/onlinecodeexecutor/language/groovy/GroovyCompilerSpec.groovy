package io.mkrzywanski.onlinecodeexecutor.language.groovy

import io.mkrzywanski.onlinecodeexecutor.language.compilation.CompilationException
import io.mkrzywanski.onlinecodeexecutor.language.file.FileOperations
import spock.lang.Shared
import spock.lang.Specification

import java.nio.file.Files

import static org.hamcrest.Matchers.hasSize

class GroovyCompilerSpec extends Specification {

    @Shared
    def tempDir = Files.createTempDirectory("groovy")

    @Shared
    def fileOperations = FileOperations.create()
    def groovyCompiler = new GroovyCompiler(tempDir, fileOperations)

    void cleanupSpec() {
        fileOperations.deleteDir(tempDir)
    }

    def "should compile groovy code"() {
        given:
        String code = "class Test {static void main(String[] args) {println \"Hello Groovy\"}}"

        when:
        def compiledClasses = groovyCompiler.compile(code)

        then:
        compiledClasses hasSize(1)

    }

    def "should throw exception when code is incorrect"() {
        given:
        String code = "\"Hello Groovy\"}}"

        when:
        groovyCompiler.compile(code)

        then:
        def exception = thrown(CompilationException)
        !exception.report.isEmpty()
    }

    def "should throw exception when there is an error creating directory"() {

    }
}
