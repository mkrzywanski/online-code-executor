package io.mkrzywanski.executor.app.domain.compilation;

import io.micronaut.http.MediaType;
import io.micronaut.http.server.types.files.FileCustomizableResponseType;
import io.micronaut.http.server.types.files.StreamedFile;
import io.mkrzywanski.executor.app.domain.compilation.api.CompileAndDownloadRequest;
import io.mkrzywanski.executor.domain.compilation.model.Code;
import io.mkrzywanski.executor.domain.compilation.exception.CompilationFailedException;
import io.mkrzywanski.executor.domain.compilation.model.CompilationResult;
import io.mkrzywanski.executor.domain.compilation.CompilationService;
import io.mkrzywanski.executor.domain.compilation.model.CompiledClasses;
import io.mkrzywanski.executor.domain.compilation.CompiledCodeCompressionService;
import io.mkrzywanski.executor.domain.compilation.exception.CompressionFailureException;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.InputStream;

@Singleton
final class CodeCompilerFacade {

    private final CompilationService compilers;
    private final CompiledCodeCompressionService codeCompressionService;

    @Inject
    CodeCompilerFacade(final CompilationService compilationService, final CompiledCodeCompressionService codeCompressionService) {
        this.compilers = compilationService;
        this.codeCompressionService = codeCompressionService;
    }

    FileCustomizableResponseType compile(final CompileAndDownloadRequest request) throws CompilationFailedException, CompressionFailureException {
        final Code code = new Code(request.getLanguage(), request.getCode());
        final CompilationResult compilationResult = compilers.compile(code);
        final CompiledClasses compiledClasses = compilationResult.getCompiledClasses();
        final InputStream compressedData = codeCompressionService.compress(compiledClasses);
        return new StreamedFile(compressedData, MediaType.MULTIPART_FORM_DATA_TYPE);

    }
}
