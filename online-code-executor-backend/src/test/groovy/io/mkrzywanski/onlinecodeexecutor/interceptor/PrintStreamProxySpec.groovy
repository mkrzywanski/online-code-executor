package io.mkrzywanski.onlinecodeexecutor.interceptor


import io.mkrzywanski.onlinecodeexecutor.language.interceptor.PrintStreamProxy
import io.mkrzywanski.onlinecodeexecutor.language.interceptor.ThreadOutputPrintStreamInterceptor
import spock.lang.Shared
import spock.lang.Specification

class PrintStreamProxySpec extends Specification {

    @Shared
    String lineSeparator = System.lineSeparator()

    @Shared
    def originalOut = System.out

    @Shared
    ThreadOutputPrintStreamInterceptor interceptor = new ThreadOutputPrintStreamInterceptor(originalOut)

    def printStreamProxy = PrintStreamProxy.create(interceptor, originalOut)

    void setup() {
        System.setOut(printStreamProxy)
    }

    def cleanup() {
        System.setOut(originalOut)
    }

    def "shouldCaptureOutputFromStandardOutput"() {
        given:

        when:
        System.out.println(1)
        System.out.println(true)
        System.out.println('c')
        System.out.print('c')

        def output = interceptor.getOutputForCurrentThread()

        then:
        output == OutputBuilder.forLineSeparator(lineSeparator)
                .appendln("1")
                .appendln("true")
                .appendln("c")
                .append("c")
                .build()
    }

    static class OutputBuilder {
        private String lineSeparator
        private StringBuilder outputBuilder

        static OutputBuilder forLineSeparator(String separator) {
            return new OutputBuilder(separator)
        }

        private OutputBuilder(String lineSeparator) {
            this.lineSeparator = lineSeparator
            this.outputBuilder = new StringBuilder()
        }

        OutputBuilder append(String s) {
            outputBuilder.append(s)
            return this
        }

        OutputBuilder appendln(String s) {
            outputBuilder.append(s).append(lineSeparator)
            return this
        }

        String build() {
            return outputBuilder.toString()
        }
    }
}
