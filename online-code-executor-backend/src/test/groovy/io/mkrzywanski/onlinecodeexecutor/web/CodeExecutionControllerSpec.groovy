package io.mkrzywanski.onlinecodeexecutor.web

import io.micronaut.http.HttpMethod
import io.micronaut.http.HttpRequest
import io.micronaut.http.client.RxHttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import io.mkrzywanski.onlinecodeexecutor.Application
import io.mkrzywanski.onlinecodeexecutor.language.Code
import io.mkrzywanski.onlinecodeexecutor.language.Language
import io.mkrzywanski.onlinecodeexecutor.language.execution.ExecutionResult
import spock.lang.Specification

import javax.inject.Inject

@MicronautTest(application = Application.class)
class CodeExecutionControllerSpec extends Specification {

    @Inject
    @Client('/')
    RxHttpClient client

    //TODO get to know why exchange returns null body
    def 'should compile code'() {
        given:
        def codeString = "public class Test { public static void main(String[] args) {System.out.println(\"hello\");}}"
        def code = new Code(Language.JAVA, codeString)

        when:
        ExecutionResult result = client.toBlocking()
                .retrieve(HttpRequest.create(HttpMethod.POST, Endpoints.EXECUTE).body(code), ExecutionResult.class)
//        String result = client.toBlocking()
//                .retrieve(HttpRequest.create(HttpMethod.POST, Endpoints.EXECUTE).body(code))

        then:
        !result.output.isEmpty()
        result.output == 'hello' + System.lineSeparator()
//        result.code() == 200
//        with(result.body()) {
//            output == 'Hello\r\n'
//        }
    }
}
