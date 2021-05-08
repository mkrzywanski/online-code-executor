package io.mkrzywanski.executor.core.compilation

import org.jetbrains.kotlin.cli.common.messages.CompilerMessageLocation
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSeverity
import spock.lang.Specification

class CustomMessageCollectorTest extends Specification {
    def "should be aware of errors when errors are reported"() {
        given:
        CustomMessageCollector collector = new CustomMessageCollector()

        when:
        collector.report(CompilerMessageSeverity.ERROR, "message", CompilerMessageLocation.create(""))

        then:
        collector.hasErrors()
    }
}