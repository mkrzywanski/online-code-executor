package io.mkrzywanski.executor.domain.compilation.model

import io.mkrzywanski.executor.test.data.CompiledClassesObjectMother
import spock.lang.Specification

class CompiledClassSpec extends Specification {
    def "should not equal"() {
        given :
        def helloWorld = CompiledClassesObjectMother.helloWorld((a, b) -> new CompiledClass(a, b))
        def other = new CompiledClass("test", new byte[]{})

        when :
        def comparisonResult = helloWorld != other

        then:
        comparisonResult
    }

    def "should equal"() {
        given :
        def helloWorld = CompiledClassesObjectMother.helloWorld((a, b) -> new CompiledClass(a, b))
        def otherHelloWorld = CompiledClassesObjectMother.helloWorld((a, b) -> new CompiledClass(a, b))

        when :
        def comparisonResult = helloWorld == otherHelloWorld

        then:
        comparisonResult
    }
}
