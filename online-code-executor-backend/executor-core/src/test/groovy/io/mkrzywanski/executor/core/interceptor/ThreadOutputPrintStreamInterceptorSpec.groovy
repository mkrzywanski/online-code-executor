package io.mkrzywanski.executor.core.interceptor

import spock.lang.Specification

class ThreadOutputPrintStreamInterceptorSpec extends Specification {
    def "should proxy calls to target object"() {
        given:
        ThreadOutputInterceptor threadOutputInterceptor = new ThreadOutputPrintStreamInterceptor(System.out)


        when:
        threadOutputInterceptor.invoke(null, PrintStream.class.getMethod("println", String.class), "hello")

        then:
        threadOutputInterceptor.getOutputForCurrentThread() == "hello" + System.lineSeparator()
    }
}
