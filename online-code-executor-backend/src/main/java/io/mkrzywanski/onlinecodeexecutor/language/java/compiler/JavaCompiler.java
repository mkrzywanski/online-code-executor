package io.mkrzywanski.onlinecodeexecutor.language.java.compiler;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseResult;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.TypeDeclaration;
import io.mkrzywanski.onlinecodeexecutor.language.compilation.CompilationException;
import io.mkrzywanski.onlinecodeexecutor.language.compilation.CompiledClass;
import io.mkrzywanski.onlinecodeexecutor.language.compilation.Compiler;
import io.mkrzywanski.onlinecodeexecutor.language.java.DiagnosticsCollectingListener;
import org.jetbrains.annotations.NotNull;

import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class JavaCompiler implements Compiler {

    private final javax.tools.JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();
    private final JavaParser javaParser = new JavaParser();

    @Override
    public Set<CompiledClass> compile(String code) throws CompilationException {

        String topLevelClassName = getTopLevelClassName(code);

        StandardJavaFileManager standardFileManager = javaCompiler.getStandardFileManager(null, null, null);
        InMemoryFileManager inMemoryFileManager = new InMemoryFileManager(standardFileManager);
        JavaSourceFromString javaSourceFromString = new JavaSourceFromString(topLevelClassName, code);
        DiagnosticsCollectingListener diagnosticListener = new DiagnosticsCollectingListener();

        javax.tools.JavaCompiler.CompilationTask task = javaCompiler.getTask(null, inMemoryFileManager, diagnosticListener, null, null, List.of(javaSourceFromString));

        Boolean isSuccess = task.call();

        ensureSuccessfulCompilation(diagnosticListener, isSuccess);

        return getCompiledClassesFromFileManager(inMemoryFileManager);

    }

    private Set<CompiledClass> getCompiledClassesFromFileManager(InMemoryFileManager inMemoryFileManager) {
        return getCompiledClasses(inMemoryFileManager);
    }

    private Set<CompiledClass> getCompiledClasses(final InMemoryFileManager inMemoryFileManager) {
        Map<String, byte[]> compiledClassBytes = inMemoryFileManager.getCompiledClassBytes();

        return compiledClassBytes.entrySet()
                .stream()
                .map(entry -> new CompiledClass(entry.getKey(), entry.getValue()))
                .collect(Collectors.toSet());
    }

    private void ensureSuccessfulCompilation(DiagnosticsCollectingListener diagnosticListener, Boolean isSuccess) throws CompilationException {
        if(!isSuccess) {
            String report = diagnosticListener.generateReport();
            throw new CompilationException("Compilation failure", report);
        }
    }

    private String getTopLevelClassName(String code) throws CompilationException {
        ParseResult<CompilationUnit> result = javaParser.parse(code);

        CompilationUnit compilationUnit = result.getResult()
                .orElseThrow(() -> new CompilationException("Compilation failure"));

        NodeList<TypeDeclaration<?>> types = compilationUnit.getTypes();

        TypeDeclaration<?> typeDeclaration = types.stream()
                .filter(TypeDeclaration::isTopLevelType)
                .findFirst()
                .orElseThrow(() -> new CompilationException("No top level class found"));

        return typeDeclaration.getNameAsString();
    }


}
