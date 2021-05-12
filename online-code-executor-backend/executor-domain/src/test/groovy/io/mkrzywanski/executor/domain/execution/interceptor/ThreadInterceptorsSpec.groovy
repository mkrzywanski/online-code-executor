package io.mkrzywanski.executor.domain.execution.interceptor

import spock.lang.Specification
import static org.hamcrest.CoreMatchers.instanceOf

class ThreadInterceptorsSpec extends Specification {

    def "should return default executor"() {
        when:
        def interceptor = ThreadInterceptors.defaultInterceptor(System.out)

        then:
        interceptor instanceOf(DefaultThreadInterceptor)
    }

}
