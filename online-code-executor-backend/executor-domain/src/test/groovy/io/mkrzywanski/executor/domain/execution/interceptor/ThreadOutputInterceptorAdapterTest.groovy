package io.mkrzywanski.executor.domain.execution.interceptor

import spock.lang.Specification

class ThreadOutputInterceptorAdapterTest extends Specification {

    def 'should forward call of getOutputForCurrentThread method'() {
        given:
        def interceptorStub = Stub(ThreadInterceptor)
        interceptorStub.getOutputForCurrentThread() >> "test"
        def adapter = new ThreadOutputInterceptorAdapter(interceptorStub)

        when:
        def thread = adapter.getOutputForCurrentThread()

        then:
        thread == "test"
    }

    def "should call proxied method"() {
        given:
        def interceptorMock = Mock(ThreadInterceptor)
        def adapter = new ThreadOutputInterceptorAdapter(interceptorMock)

        when:
        adapter.removeForCurrentThread()

        then:
        1 * interceptorMock.removeForCurrentThread()
    }
}
