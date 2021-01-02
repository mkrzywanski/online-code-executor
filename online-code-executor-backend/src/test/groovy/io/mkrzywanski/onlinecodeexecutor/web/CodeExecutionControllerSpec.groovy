package io.mkrzywanski.onlinecodeexecutor.web

import io.micronaut.http.HttpMethod
import io.micronaut.http.HttpRequest
import io.micronaut.http.client.RxHttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import io.mkrzywanski.onlinecodeexecutor.language.Code
import io.mkrzywanski.onlinecodeexecutor.language.Language
import spock.lang.Specification

import javax.inject.Inject

@MicronautTest
class CodeExecutionControllerSpec extends Specification {

    @Inject
    @Client('/')
    RxHttpClient client

    def 'should compile code'() {
        given:
        def codeString = "public class Test { public static void main(String[] args) {System.out.println(\"hello\");}}"
        def code = new Code(Language.JAVA, codeString)

        when:
        def result = client.toBlocking()
                .exchange(HttpRequest.create(HttpMethod.POST, Endpoints.EXECUTE).body(code))

        then:
        result.code() == 200
    }
}
