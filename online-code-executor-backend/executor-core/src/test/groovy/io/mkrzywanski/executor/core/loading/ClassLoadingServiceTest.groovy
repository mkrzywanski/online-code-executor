package io.mkrzywanski.executor.core.loading

import io.mkrzywanski.executor.core.compilation.CompiledClass
import io.mkrzywanski.executor.core.compilation.CompiledClasses
import io.mkrzywanski.executor.test.data.CompiledClassesObjectMother
import spock.lang.Shared
import spock.lang.Specification

class ClassLoadingServiceTest extends Specification {

    @Shared
    def classLoadingService = new ClassLoadingService()

    def mapper = (a, b) -> new CompiledClasses(Set.of(new CompiledClass(a, b)))

    def "should load classes"() {

        given:
        CompiledClasses compiledClasses = CompiledClassesObjectMother.helloWorld(mapper)

        when:
        def loadedClasses = classLoadingService.load(compiledClasses)

        then:
        loadedClasses.mainClass().name == compiledClasses.asSet().first().name
    }
}
