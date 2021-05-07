package io.mkrzywanski.executor.domain.compilation


import io.mkrzywanski.executor.domain.data.CompiledClassesObjectMother
import spock.lang.Specification

import static io.mkrzywanski.executor.test.matchers.ContainsNumberOfZippedFiles.containsNumberOfZippedFiles
import static org.hamcrest.MatcherAssert.assertThat

class CompiledCodeCompressionServiceSpec extends Specification {

    def codeCompressionService = CompiledCodeCompressionService.newInstance();

    def "should compress"() {
        given:
        def compiledClasses = CompiledClassesObjectMother.helloWorld()

        when:
        def compressedStream = codeCompressionService.compress(compiledClasses)

        then:
        assertThat(compressedStream, containsNumberOfZippedFiles(1))
    }
}
