package io.mkrzywanski.onlinecodeexecutor.web;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.mkrzywanski.onlinecodeexecutor.language.execution.ExecutionException;
import io.mkrzywanski.onlinecodeexecutor.language.execution.ExecutionService;
import io.mkrzywanski.onlinecodeexecutor.language.compilation.CompilationException;
import io.mkrzywanski.onlinecodeexecutor.language.Code;
import io.mkrzywanski.onlinecodeexecutor.language.execution.ExecutionResult;

import javax.inject.Inject;

@Controller
public class CodeExecutionController {

    private final ExecutionService executionService;

    @Inject
    public CodeExecutionController(ExecutionService executionService) {
        this.executionService = executionService;
    }

    @Post(uri = Endpoints.EXECUTE, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    public HttpResponse<ExecutionResult> execute(@Body Code code) throws CompilationException, ExecutionException {
        ExecutionResult executionResult = executionService.execute(code);
        return HttpResponse.ok(executionResult);
    }
}
