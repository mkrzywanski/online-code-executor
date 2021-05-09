package io.mkrzywanski.executor.domain.execution.interceptor

import spock.lang.Specification

import java.lang.reflect.Method

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

    def "should call proxied removeForCurrentThread method"() {
        given:
        def interceptorMock = Mock(ThreadInterceptor)
        def adapter = new ThreadOutputInterceptorAdapter(interceptorMock)

        when:
        adapter.removeForCurrentThread()

        then:
        1 * interceptorMock.removeForCurrentThread()
    }

    def "should call proxied invoke method"() {
        given:
        def interceptorMock = Mock(ThreadInterceptor)
        def adapter = new ThreadOutputInterceptorAdapter(interceptorMock)
        def targetMock = new Object()
        def targetMethod  = Object.class.getMethod("toString")
        def args = new Object[] {}

        when:
        adapter.invoke(targetMock, targetMethod, args)

        then:
        1 * interceptorMock.invoke(targetMock, targetMethod, args)
    }
}
