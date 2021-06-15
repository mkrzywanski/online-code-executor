package io.mkrzywanski.executor.core.compilation

import org.jetbrains.kotlin.cli.common.messages.CompilerMessageLocation
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSeverity
import spock.lang.Specification

class CustomMessageCollectorSpec extends Specification {
    def "should be aware of errors when errors are reported"() {
        given:
        CustomMessageCollector collector = new CustomMessageCollector()

        when:
        collector.report(CompilerMessageSeverity.ERROR, "message", CompilerMessageLocation.create(""))

        then:
        collector.hasErrors()
    }

    def "should report nothing when clear is called"() {
        given:
        CustomMessageCollector collector = new CustomMessageCollector()

        when:
        collector.report(CompilerMessageSeverity.ERROR, "message", CompilerMessageLocation.create(""))
        collector.clear()
        
        then:
        collector.report() == ''
    }
}
