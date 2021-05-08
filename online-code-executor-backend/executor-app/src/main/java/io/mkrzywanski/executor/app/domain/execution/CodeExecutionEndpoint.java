package io.mkrzywanski.executor.app.domain.execution;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.mkrzywanski.executor.app.domain.execution.api.ExecuteCodeRequest;
import io.mkrzywanski.executor.app.domain.execution.api.ExecuteCodeResponse;
import io.mkrzywanski.executor.app.infra.web.Endpoints;
import io.mkrzywanski.executor.domain.compilation.exception.CompilationFailedException;
import io.mkrzywanski.executor.domain.execution.exception.ExecutionFailedException;

import javax.inject.Inject;

@Controller
final class CodeExecutionEndpoint {

    private final CodeExecutionFacade codeExecutionFacade;

    @Inject
    CodeExecutionEndpoint(final CodeExecutionFacade codeExecutionFacade) {
        this.codeExecutionFacade = codeExecutionFacade;
    }

    @Post(uri = Endpoints.EXECUTE, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    HttpResponse<ExecuteCodeResponse> execute(@Body final ExecuteCodeRequest request) throws CompilationFailedException, ExecutionFailedException {
        final ExecuteCodeResponse executeCodeResponse = codeExecutionFacade.executeCode(request);
        return HttpResponse.ok(executeCodeResponse);
    }
}
