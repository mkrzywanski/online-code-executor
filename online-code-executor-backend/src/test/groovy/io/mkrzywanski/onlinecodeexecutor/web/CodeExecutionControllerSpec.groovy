package io.mkrzywanski.onlinecodeexecutor.web

import io.micronaut.http.HttpMethod
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.client.RxHttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import io.mkrzywanski.onlinecodeexecutor.language.Code
import io.mkrzywanski.onlinecodeexecutor.language.Language
import io.mkrzywanski.onlinecodeexecutor.language.execution.ExecutionResult
import spock.lang.Shared
import spock.lang.Specification

import javax.inject.Inject

@MicronautTest
class CodeExecutionControllerSpec extends Specification {

    @Shared
    @Inject
    @Client('/')
    RxHttpClient client

    def 'should compile and execute Java code'() {
        given:
        def codeString = "public class Test { public static void main(String[] args) {System.out.println(\"hello\");}}"
        def code = new Code(Language.JAVA, codeString)

        when:
        HttpResponse<ExecutionResult> result = client.toBlocking()
                .exchange(HttpRequest.create(HttpMethod.POST, Endpoints.EXECUTE).body(code), ExecutionResult)

        then:
        result.code() == 200
        with(result.body()) {
            output == 'hello' + System.lineSeparator()
        }
    }

    def 'should compile and execute Groovy code'() {
        given:
        def codeString = "class Test {static void main(String[] args) {println \"Hello Groovy\"}}"
        def code = new Code(Language.GROOVY, codeString)

        when:
        HttpResponse<ExecutionResult> result = client.toBlocking()
                .exchange(HttpRequest.create(HttpMethod.POST, Endpoints.EXECUTE).body(code), ExecutionResult.class)

        then:
        result.code() == 200
        with(result.body()) {
            output == 'Hello Groovy' + System.lineSeparator()
        }
    }
}
