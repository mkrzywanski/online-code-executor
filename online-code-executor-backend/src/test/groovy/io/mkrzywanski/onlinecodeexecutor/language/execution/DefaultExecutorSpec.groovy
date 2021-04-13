package io.mkrzywanski.onlinecodeexecutor.language.execution

import io.mkrzywanski.data.HelloWorld
import io.mkrzywanski.onlinecodeexecutor.language.interceptor.ThreadOutputInterceptor
import io.mkrzywanski.onlinecodeexecutor.language.loading.LoadedClasses
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
