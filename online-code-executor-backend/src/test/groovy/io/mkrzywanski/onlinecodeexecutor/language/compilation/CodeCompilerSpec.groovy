package io.mkrzywanski.onlinecodeexecutor.language.compilation

import io.mkrzywanski.onlinecodeexecutor.language.Code
import io.mkrzywanski.onlinecodeexecutor.language.Language
import io.mkrzywanski.onlinecodeexecutor.language.java.compiler.JavaCompiler
import spock.lang.Shared
import spock.lang.Specification

class CodeCompilerSpec extends Specification {

    def compilers = Stub(Compilers)
    def codeCompiler = new CodeCompiler(compilers)

    def "should compile code for given language"() {
        given:
        def code = "public class Test { public static void main(String[] args) {System.out.println(\"hello\");}}"
        and:
        compilers.forLanguage(Language.JAVA) >> new JavaCompiler()

        when:
        def compile = codeCompiler.compile(new Code(Language.JAVA, code))

        then:
        compile.compiledClasses.size() == 1
    }
}
