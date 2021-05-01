package io.mkrzywanski.executor.app.domain.compilation;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.server.types.files.FileCustomizableResponseType;
import io.mkrzywanski.executor.app.domain.compilation.api.CompileAndDownloadRequest;
import io.mkrzywanski.executor.domain.compilation.exception.CompilationFailedException;
import io.mkrzywanski.executor.domain.compilation.exception.CompressionFailureException;

import javax.inject.Inject;

import static io.mkrzywanski.executor.app.infra.web.Endpoints.COMPILE_AND_COMPRESS;

@Controller
final class CodeCompilationEndpoint {

    private final CodeCompilerFacade codeCompilerFacade;

    @Inject
    CodeCompilationEndpoint(final CodeCompilerFacade codeCompilerFacade) {
        this.codeCompilerFacade = codeCompilerFacade;
    }

    @Post(value = COMPILE_AND_COMPRESS, consumes = MediaType.APPLICATION_JSON, produces = MediaType.MULTIPART_FORM_DATA)
    FileCustomizableResponseType compileAndDownload(@Body final CompileAndDownloadRequest compileAndDownloadRequest) throws CompilationFailedException, CompressionFailureException {
        return codeCompilerFacade.compile(compileAndDownloadRequest);
    }

}
