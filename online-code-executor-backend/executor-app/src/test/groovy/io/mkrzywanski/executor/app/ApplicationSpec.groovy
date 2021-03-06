package io.mkrzywanski.executor.app

import io.micronaut.runtime.EmbeddedApplication
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import spock.lang.Specification
import javax.inject.Inject

@MicronautTest
class ApplicationSpec extends Specification {

    @Inject
    EmbeddedApplication<? extends EmbeddedApplication> application

    void 'test it works'() {
        expect:
        application.running
    }

}
