package io.mkrzywanski.executor.app.domain.execution

import io.micronaut.http.HttpMethod
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.client.RxHttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import io.mkrzywanski.executor.app.domain.execution.api.ExecuteCodeRequest
import io.mkrzywanski.executor.app.domain.execution.api.ExecuteCodeResponse
import io.mkrzywanski.executor.app.infra.web.Endpoints
import io.mkrzywanski.executor.domain.common.Language
import io.mkrzywanski.executor.test.data.CodeData
import spock.lang.Shared
import spock.lang.Specification

import javax.inject.Inject

@MicronautTest
class CodeExecutionEndpointIntegSpec extends Specification {

    @Shared
    @Inject
    @Client('/')
    RxHttpClient client

    def 'should compile and execute Java code'() {
        given:
        def codeString = CodeData.Java.HELLO_WORLD
        def code = new ExecuteCodeRequest(Language.JAVA, codeString)

        when:
        HttpResponse<ExecuteCodeResponse> response = client
                .exchange(HttpRequest.create(HttpMethod.POST, Endpoints.EXECUTE).body(code), ExecuteCodeResponse)
                .blockingSingle()

        then:
        response.code() == 200
        with(response.body()) {
             result == 'hello' + System.lineSeparator()
        }
    }

    def 'should compile and execute Groovy code'() {
        given:
        def codeString = CodeData.Groovy.HELLO_WORLD
        def code = new ExecuteCodeRequest(Language.GROOVY, codeString)

        when:
        HttpResponse<ExecuteCodeResponse> response = client.toBlocking()
                .exchange(HttpRequest.create(HttpMethod.POST, Endpoints.EXECUTE).body(code), ExecuteCodeResponse.class)

        then:
        response.code() == 200
        with(response.body()) {
            result == 'Hello Groovy' + System.lineSeparator()
        }
    }

    def 'should compile and execute kotlin code'() {
        given:
        def codeString = CodeData.Kotlin.HELLO_WORLD
        def code = new ExecuteCodeRequest(Language.KOTLIN, codeString)

        when:
        HttpResponse<ExecuteCodeResponse> response = client.toBlocking()
                .exchange(HttpRequest.create(HttpMethod.POST, Endpoints.EXECUTE).body(code), ExecuteCodeResponse.class)

        then:
        response.code() == 200
        with(response.body()) {
            result == 'Hello, world!' + System.lineSeparator()
        }
    }
}
