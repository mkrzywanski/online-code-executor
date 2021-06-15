package io.mkrzywanski.executor.core.loading

import io.mkrzywanski.executor.core.compilation.CompilationException
import io.mkrzywanski.executor.core.compilation.CompiledClass
import io.mkrzywanski.executor.core.compilation.CompiledClasses
import io.mkrzywanski.executor.test.data.CompiledClassesObjectMother
import org.codehaus.groovy.control.CompilationFailedException
import spock.lang.Shared
import spock.lang.Specification

class ClassLoadingServiceSpec extends Specification {

    @Shared
    def classLoadingService = ClassLoadingService.newInstance()

    def mapper = (a, b) -> new CompiledClasses(Set.of(new CompiledClass(a, b)))

    def "should load classes"() {

        given:
        CompiledClasses compiledClasses = CompiledClassesObjectMother.helloWorld(mapper)

        when:
        def loadedClasses = classLoadingService.load(compiledClasses)

        then:
        loadedClasses.mainClass().name == compiledClasses.asSet().first().name
    }

    def "should throw compilation exception"() {
        given:
        CompiledClasses compiledClasses = CompiledClassesObjectMother.helloWorld(mapper)
        ClassLoader classLoader = Mock(ClassLoader)
        classLoader.loadClass(compiledClasses.asSet().first().name) >> { String user ->
            throw new ClassNotFoundException("aaa")
        }
        ClassLoaderProvider classLoaderProvider = classBytes -> classLoader
        ClassLoadingService sut = new ClassLoadingService(classLoaderProvider)

        when:
        sut.load(compiledClasses)

        then:
        thrown(ClassLoadingException)
    }
}
