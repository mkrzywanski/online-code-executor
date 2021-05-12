package io.mkrzywanski.executor.app.domain.execution

import io.micronaut.core.type.Argument
import io.micronaut.http.HttpMethod
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.MutableHttpRequest
import io.micronaut.http.client.RxHttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import io.mkrzywanski.executor.app.domain.compilation.api.CompileAndDownloadRequest
import io.mkrzywanski.executor.app.domain.execution.api.ExecuteCodeRequest
import io.mkrzywanski.executor.app.domain.execution.api.ExecuteCodeResponse
import io.mkrzywanski.executor.app.infra.web.Endpoints
import io.mkrzywanski.executor.app.infra.web.handler.ErrorResponse
import io.mkrzywanski.executor.domain.common.Language
import io.mkrzywanski.executor.test.data.CodeData
import spock.lang.Shared
import spock.lang.Specification

import javax.inject.Inject

import static org.hamcrest.MatcherAssert.assertThat
import static org.hamcrest.Matchers.emptyOrNullString
import static org.hamcrest.Matchers.equalTo
import static org.hamcrest.Matchers.is
import static org.hamcrest.Matchers.not

@MicronautTest
class CodeExecutionEndpointIntegSpec extends Specification {

    @Shared
    @Inject
    @Client(value = '/', errorType = ErrorResponse)
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
        def request = HttpRequest.create(HttpMethod.POST, Endpoints.EXECUTE).body(code)

        when:
        HttpResponse<ExecuteCodeResponse> response = client.toBlocking()
                .exchange(request, ExecuteCodeResponse.class)

        then:
        response.code() == 200
        with(response.body()) {
            result == 'Hello, world!' + System.lineSeparator()
        }
    }

    def "should fail when code cannot be compiled"() {
        given:
        def codeString = "wrong code"
        def code = new CompileAndDownloadRequest(Language.JAVA, codeString)
        def request = HttpRequest.create(HttpMethod.POST, Endpoints.EXECUTE)

        when:
        client.toBlocking()
                .exchange(request.body(code), Argument.of(byte[]), Argument.of(ErrorResponse))

        then:
        def exception = thrown(HttpClientResponseException)
        exception.status == HttpStatus.BAD_REQUEST
        def errorResponse = exception.response.getBody(ErrorResponse).get()
        errorResponse.httpStatus == 400
        assertThat(errorResponse.getMessage(), is(not(emptyOrNullString())))
    }

    def "should fail when code cannot be executed"() {
        given:
        def codeString = CodeData.Java.EMPTY_CLASS
        def code = new CompileAndDownloadRequest(Language.JAVA, codeString)
        def request = HttpRequest.create(HttpMethod.POST, Endpoints.EXECUTE)

        when:
        client.toBlocking()
                .exchange(request.body(code), Argument.of(byte[]), Argument.of(ErrorResponse))

        then:
        def exception = thrown(HttpClientResponseException)
        exception.status == HttpStatus.BAD_REQUEST
        def errorResponse = exception.response.getBody(ErrorResponse).get()
        errorResponse.httpStatus == 400
        assertThat(errorResponse.getMessage(), is(equalTo("No main method found")))
    }
}
