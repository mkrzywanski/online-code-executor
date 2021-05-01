package io.mkrzywanski.executor.app.domain.compilation

import io.micronaut.http.HttpMethod
import io.micronaut.http.HttpRequest
import io.micronaut.http.client.RxHttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.http.server.types.files.StreamedFile
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import io.mkrzywanski.executor.app.domain.compilation.api.CompileAndDownloadRequest
import io.mkrzywanski.executor.app.infra.web.Endpoints
import io.mkrzywanski.executor.app.utils.PotentialClass
import io.mkrzywanski.executor.app.utils.TestClassLoader
import io.mkrzywanski.executor.domain.common.Language
import net.lingala.zip4j.io.inputstream.ZipInputStream
import net.lingala.zip4j.model.LocalFileHeader
import spock.lang.Shared
import spock.lang.Specification

import javax.inject.Inject

import static org.hamcrest.MatcherAssert.assertThat
import static org.hamcrest.Matchers.contains
import static org.hamcrest.Matchers.hasSize
import static org.hamcrest.Matchers.equalTo

@MicronautTest
class CodeCompilationEndpointTest extends Specification {

    @Shared
    @Inject
    @Client("/")
    RxHttpClient httpClient

    def "should return compressed files"() {
        given:
        def codeString = "public class Test { public static void main(String[] args) {System.out.println(\"hello\");}}"
        def code = new CompileAndDownloadRequest(Language.JAVA, codeString)
        def request = HttpRequest.create(HttpMethod.POST, Endpoints.COMPILE_AND_COMPRESS)

        when:
        def response = httpClient.toBlocking()
                .exchange(request.body(code), byte[].class).getBody().get()

        then:
        List<PotentialClass> potentialClasses = extractClassesFromResponse(response)
        potentialClasses hasSize(1)
        potentialClasses.first().getName() == 'Test'

        classesCanBeLoaded(potentialClasses)
        noExceptionThrown()

    }

    def "should return compressed files when there are more than 1 class compile"() {
        given:
        def codeString =
                """public class Test {
                        public static void main(String[] args) {
                            System.out.println(\"HelloWorld\");
                        }
                        public class NestedClass {
                        
                        }
                }"""
        def code = new CompileAndDownloadRequest(Language.JAVA, codeString)
        def request = HttpRequest.create(HttpMethod.POST, Endpoints.COMPILE_AND_COMPRESS)

        when:
        def response = httpClient.toBlocking()
                .exchange(request.body(code), byte[].class).getBody().get()

        then:
        List<PotentialClass> potentialClasses = extractClassesFromResponse(response)
        potentialClasses hasSize(2)
        assertThat(potentialClasses*.name ,contains('Test', 'Test$NestedClass'))

        classesCanBeLoaded(potentialClasses)
        noExceptionThrown()

    }

    private static void classesCanBeLoaded(List<PotentialClass> potentialClasses) {
        final TestClassLoader testClassLoader = new TestClassLoader(potentialClasses)
        potentialClasses.each {
            testClassLoader.loadClass(it.name)
        }
    }

    private static List<PotentialClass> extractClassesFromResponse(byte[] body) {
        List<PotentialClass> classesBytes = new ArrayList<>()
        LocalFileHeader localFileHeader

        InputStream inputStream = new ByteArrayInputStream(body)
        try (ZipInputStream zipInputStream = new ZipInputStream(inputStream)) {
            while ((localFileHeader = zipInputStream.getNextEntry()) != null) {
                def bytes = readSingleEntry(zipInputStream)
                classesBytes.add(new PotentialClass(localFileHeader.fileName, bytes))
            }
        }
        return classesBytes
    }

    private static byte[] readSingleEntry(ZipInputStream zipInputStream) {
        int readLen
        byte[] readBuffer = new byte[4096]

        OutputStream outputStream = new ByteArrayOutputStream()
        while ((readLen = zipInputStream.read(readBuffer)) != -1) {
            outputStream.write(readBuffer, 0, readLen);
        }
        outputStream.close()
        outputStream.toByteArray()
    }
}
