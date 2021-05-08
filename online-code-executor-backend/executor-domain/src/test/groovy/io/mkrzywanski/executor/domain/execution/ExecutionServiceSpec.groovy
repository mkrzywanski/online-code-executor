package io.mkrzywanski.executor.domain.execution

import io.mkrzywanski.executor.domain.compilation.model.CompiledClass
import io.mkrzywanski.executor.domain.compilation.model.CompiledClasses
import io.mkrzywanski.executor.domain.execution.interceptor.ThreadInterceptor
import io.mkrzywanski.executor.test.data.CompiledClassesObjectMother
import spock.lang.Specification

class ExecutionServiceSpec extends Specification {

    def executionService
    def compiledClasses

    void setup() {
        def stub = Stub(ThreadInterceptor)
        stub.getOutputForCurrentThread() >> 'HelloWorld\n'
        executionService = new ExecutionService(stub)

        def mapper = (a, b) -> {
            return new CompiledClasses(Set.of(new CompiledClass(a, b)))
        }
        compiledClasses = CompiledClassesObjectMother.helloWorld(mapper)
    }

    def "should execute code"() {
        given:

        when:
        def execute = executionService.execute(compiledClasses)

        then:
        execute.output == 'HelloWorld\n'
    }
}
