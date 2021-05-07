package io.mkrzywanski.executor.core.loading

import io.mkrzywanski.executor.core.data.CompiledClassesObjectMother
import io.mkrzywanski.executor.core.compilation.CompiledClasses
import spock.lang.Shared
import spock.lang.Specification

class ClassLoadingServiceTest extends Specification {

    @Shared
    def classLoadingService = new ClassLoadingService()

    def "should load classes"() {
        given:
        CompiledClasses classes = CompiledClassesObjectMother.helloWorld()

        when:
        def loadedClasses = classLoadingService.load(classes)

        then:
        loadedClasses.mainClass().name == classes.asSet().first().name
    }
}
