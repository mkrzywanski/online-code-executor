package io.mkrzywanski.executor.core.compilation

import spock.lang.Shared
import spock.lang.Specification

import static org.hamcrest.Matchers.hasSize

class JavaCompilerSpec extends Specification {

    @Shared
    def javaCompiler = new JavaCompiler()

    def "should compile java code"() {
        given:
        String code = "public class Test { public static void main(String[] args) {System.out.println(\"hello\");}}"

        when:
        def compile = javaCompiler.compile(code)

        then:
        compile hasSize(1)
    }
}
