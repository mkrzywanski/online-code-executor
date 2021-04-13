package io.mkrzywanski.onlinecodeexecutor.language.groovy;

import groovy.lang.GroovyClassLoader;
import io.mkrzywanski.onlinecodeexecutor.language.compilation.CompilationException;
import io.mkrzywanski.onlinecodeexecutor.language.compilation.CompiledClass;
import io.mkrzywanski.onlinecodeexecutor.language.compilation.Compiler;
import io.mkrzywanski.onlinecodeexecutor.language.execution.ExecutionId;
import io.mkrzywanski.onlinecodeexecutor.language.file.FileOperations;
import org.codehaus.groovy.control.CompilationFailedException;
import org.codehaus.groovy.control.CompilationUnit;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.codehaus.groovy.control.Janitor;
import org.codehaus.groovy.tools.GroovyClass;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class GroovyCompiler implements Compiler {

    private final Path compilationBaseDirectory;
    private final FileOperations fileOperations;

    public GroovyCompiler(final Path compilationBaseDirectory, FileOperations fileOperations) {
        this.compilationBaseDirectory = compilationBaseDirectory;
        this.fileOperations = fileOperations;
    }

    @Override
    public Set<CompiledClass> compile(final String code) throws CompilationException {

        String executionId = ExecutionId.generate().asString();

        Path compilationDirectoryPath = Paths.get(compilationBaseDirectory + "/" + executionId);
        createCompilationDirectory(compilationDirectoryPath);

        final CompilerConfiguration conf = getCompilerConfiguration(compilationDirectoryPath);

        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();

        GroovyClassLoader groovyClassLoader = new GroovyClassLoader(contextClassLoader, conf, false);

        CompilationUnit unit = new CompilationUnit(conf, null, groovyClassLoader);
        unit.addSource("test", code);

        compile(unit);

        List<GroovyClass> classes = unit.getClasses();

        Set<CompiledClass> compiledClasses = classes.stream()
                .map(groovyClass -> new CompiledClass(groovyClass.getName(), groovyClass.getBytes()))
                .collect(Collectors.toSet());

        deleteDirectory(compilationDirectoryPath);
        return compiledClasses;

    }

    private void compile(CompilationUnit unit) throws CompilationException {
        try {
            unit.compile();
        } catch (CompilationFailedException e) {
            String errorReport = getErrorReport(unit);
            throw new CompilationException("Compilation error", errorReport);
        }
    }

    private String getErrorReport(CompilationUnit unit) {
        StringWriter stringWriter = new StringWriter();
        try(PrintWriter printWriter = new PrintWriter(stringWriter)) {
            unit.getErrorCollector().write(printWriter, new Janitor());
        }
        return stringWriter.toString();
    }

    private void deleteDirectory(final Path compilationDirectoryPath) throws CompilationException {
        try {
            fileOperations.deleteDir(compilationDirectoryPath);
        } catch (IOException e) {
            throw new CompilationException(e);
        }
    }

    @NotNull
    private CompilerConfiguration getCompilerConfiguration(Path compilationDirectoryPath) {
        final CompilerConfiguration conf = new CompilerConfiguration();
        conf.setTolerance(0);
        conf.setVerbose(true);
        conf.setTargetDirectory(compilationDirectoryPath.toFile());
        return conf;
    }

    private void createCompilationDirectory(Path compilationDirectory) throws CompilationException {
        File compilationDir = new File(compilationDirectory.toUri());
        boolean mkdirs = compilationDir.mkdirs();

        if(!mkdirs) {
            throw new CompilationException("Could not crete directory");
        }
    }
}
