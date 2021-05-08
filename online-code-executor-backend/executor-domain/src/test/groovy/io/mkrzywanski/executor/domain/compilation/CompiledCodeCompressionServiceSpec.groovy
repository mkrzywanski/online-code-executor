package io.mkrzywanski.executor.domain.compilation

import io.mkrzywanski.executor.domain.compilation.model.CompiledClass
import io.mkrzywanski.executor.domain.compilation.model.CompiledClasses
import io.mkrzywanski.executor.test.data.CompiledClassesObjectMother
import spock.lang.Specification

import static io.mkrzywanski.executor.test.matchers.ContainsNumberOfZippedFiles.containsNumberOfZippedFiles
import static org.hamcrest.MatcherAssert.assertThat

class CompiledCodeCompressionServiceSpec extends Specification {

    def codeCompressionService = CompiledCodeCompressionService.newInstance()
    def compiledClassMapper = (name, bytes) -> {
        return new CompiledClasses(Set.of(new CompiledClass(name, bytes)))
    }

    def "should compress"() {
        given:
        def compiledClasses = CompiledClassesObjectMother.helloWorld(compiledClassMapper)

        when:
        def compressedStream = codeCompressionService.compress(compiledClasses)

        then:
        assertThat(compressedStream, containsNumberOfZippedFiles(1))
    }
}
