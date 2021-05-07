package io.mkrzywanski.executor.domain.compilation

import io.mkrzywanski.executor.domain.common.Language
import io.mkrzywanski.executor.domain.compilation.exception.CompilationFailedException
import io.mkrzywanski.executor.domain.compilation.model.Code
import spock.lang.Shared
import spock.lang.Specification

import static org.hamcrest.MatcherAssert.assertThat
import static org.hamcrest.Matchers.hasSize

class CompilationServiceSpec extends Specification {

    @Shared
    def compilers = DefaultCompilers.CompilerBuilder.newInstance()
            .withJavaEnabled()
            .build()

    @Shared
    def compilationService = new CompilationService(compilers)

    def "should compile code"() {
        given:
        def codeString = """public class Test {
                                public static void main(String[] args) {
                                    System.out.println(\"hello\");
                                }
                            }"""
        def code = new Code(Language.JAVA, codeString)

        when:
        def compilationResult = compilationService.compile(code)

        then:
        noExceptionThrown()
        assertThat(compilationResult.compiledClasses.asSet(), hasSize(1))
    }

    def "should throw exception when code cannot be compiled"() {
        given:
        def codeString = "invalid code"
        def code = new Code(Language.JAVA, codeString)

        when:
        compilationService.compile(code)

        then:
        thrown(CompilationFailedException)
    }
}
