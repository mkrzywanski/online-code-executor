package io.mkrzywanski.executor.core.compilation

import spock.lang.Specification

import javax.tools.Diagnostic
import javax.tools.JavaFileObject

import static org.hamcrest.MatcherAssert.assertThat
import static org.hamcrest.Matchers.emptyString
import static org.hamcrest.Matchers.isEmptyString
import static org.hamcrest.Matchers.not

class DiagnosticsCollectingListenerTest extends Specification {

    def diag = new DiagnosticsCollectingListener()

    def "should generate report when diagnostics are reported"() {
        given:
        def mock = Stub(Diagnostic)
        mock.kind >> Diagnostic.Kind.ERROR

        when:
        diag.report(mock)

        then:
        assertThat(diag.generateReport(), not(emptyString()))

    }
}
