package io.mkrzywanski.onlinecodeexecutor.language.groovy

import io.mkrzywanski.onlinecodeexecutor.language.compilation.CompilationException
import io.mkrzywanski.onlinecodeexecutor.language.compilation.CompiledClass
import spock.lang.Shared
import spock.lang.Specification

import java.nio.file.Files
import java.nio.file.Path

import org.apache.commons.io.FileUtils

import static org.hamcrest.Matchers.hasSize

class GroovyCompilerTest extends Specification {
    @Shared
    Path tempDir = Files.createTempDirectory("groovy");

    @Shared
    GroovyCompiler groovyCompiler = new GroovyCompiler(tempDir)

    void cleanupSpec() {
        FileUtils.deleteDirectory(tempDir.toFile())
    }

    def "should compile groovy code"() {
        given:
        String code = "class Test {static void main(String[] args) {println \"Hello Groovy\"}}"

        when:
        def compile = groovyCompiler.compile(code)

        then:
        compile hasSize(1)

    }

    def "should throw exception when code is incorrect"() {
        given:
        String code = "\"Hello Groovy\"}}"

        when:
        groovyCompiler.compile(code)

        then:
        def exception =  thrown(CompilationException)
        !exception.report.isEmpty()
    }
}
