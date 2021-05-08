package io.mkrzywanski.executor.core.compilation

import spock.lang.Specification

import java.nio.file.Path

import static org.hamcrest.CoreMatchers.instanceOf;

class CompilersSpec extends Specification {
    def "should return java executor"() {
        when:
        def actual = Compilers.java()

        then:
        actual instanceOf(JavaCompiler)
    }

    def "should return groovy compiler"() {
        when:
        def actual = Compilers.groovy(Path.of(""))

        then:
        actual instanceOf(GroovyCompiler)
    }

    def "should return kotlin compiler"() {
        when:
        def actual = Compilers.kotlin(Path.of(""))

        then:
        actual instanceOf(KotlinCompiler)
    }

}
