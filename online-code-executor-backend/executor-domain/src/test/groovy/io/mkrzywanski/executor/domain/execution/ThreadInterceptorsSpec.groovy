package io.mkrzywanski.executor.domain.execution

import io.mkrzywanski.executor.domain.execution.DefaultThreadInterceptor
import io.mkrzywanski.executor.domain.execution.ThreadInterceptors
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
