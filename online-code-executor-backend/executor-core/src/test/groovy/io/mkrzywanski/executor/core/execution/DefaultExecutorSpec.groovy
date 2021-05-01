package io.mkrzywanski.executor.core.execution

import io.mkrzywanski.executor.core.data.HelloWorld
import io.mkrzywanski.executor.core.interceptor.ThreadOutputInterceptor
import io.mkrzywanski.executor.core.loading.LoadedClasses
import spock.lang.Specification

class DefaultExecutorSpec extends Specification {

    def interceptor = Stub(ThreadOutputInterceptor)
    def executor = new DefaultExecutor(interceptor)

    def "should return execution result"() {
        given:
        def mockedResponse = "Hello"
        interceptor.outputForCurrentThread >> mockedResponse
        LoadedClasses loadedClasses = new LoadedClasses(Set.of(HelloWorld))
        when:
        def execute = executor.execute(loadedClasses)

        then:
        execute.output == mockedResponse
    }
}
