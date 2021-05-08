package io.mkrzywanski.executor.core.compilation;

import groovy.lang.GroovyClassLoader;
import org.codehaus.groovy.control.CompilationFailedException;
import org.codehaus.groovy.control.CompilationUnit;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.codehaus.groovy.control.Janitor;
import org.codehaus.groovy.tools.GroovyClass;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

class GroovyCompiler implements Compiler {

    private final Path compilationBaseDirectory;
    private final FileOperations fileOperations;

    GroovyCompiler(final Path compilationBaseDirectory, final FileOperations fileOperations) {
        this.compilationBaseDirectory = compilationBaseDirectory;
        this.fileOperations = fileOperations;
    }

    @Override
    public Set<CompiledClass> compile(final String code) throws CompilationException {

        final String executionId = CompilationId.generate().asString();

        final Path compilationDirectoryPath = Paths.get(compilationBaseDirectory + "/" + executionId);
        createCompilationDirectory(compilationDirectoryPath);

        final CompilerConfiguration conf = getCompilerConfiguration(compilationDirectoryPath);
        final ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        final GroovyClassLoader groovyClassLoader = new GroovyClassLoader(contextClassLoader, conf, false);

        final CompilationUnit unit = new CompilationUnit(conf, null, groovyClassLoader);
        unit.addSource("test", code);
        compile(unit);

        final List<GroovyClass> classes = unit.getClasses();
        final Set<CompiledClass> compiledClasses = classes.stream()
                .map(groovyClass -> new CompiledClass(groovyClass.getName(), groovyClass.getBytes()))
                .collect(Collectors.toSet());

        deleteDirectory(compilationDirectoryPath);
        return compiledClasses;

    }

    private void compile(final CompilationUnit unit) throws CompilationException {
        try {
            unit.compile();
        } catch (final CompilationFailedException e) {
            final String errorReport = getErrorReport(unit);
            throw new CompilationException("Compilation error", errorReport);
        }
    }

    private String getErrorReport(final CompilationUnit unit) {
        final StringWriter stringWriter = new StringWriter();
        try (PrintWriter printWriter = new PrintWriter(stringWriter)) {
            unit.getErrorCollector().write(printWriter, new Janitor());
        }
        return stringWriter.toString();
    }

    private void deleteDirectory(final Path compilationDirectoryPath) throws CompilationException {
        try {
            fileOperations.deleteDir(compilationDirectoryPath);
        } catch (final IOException e) {
            throw new CompilationException(e);
        }
    }

    private CompilerConfiguration getCompilerConfiguration(final Path compilationDirectoryPath) {
        final CompilerConfiguration conf = new CompilerConfiguration();
        conf.setTolerance(0);
        conf.setVerbose(true);
        conf.setTargetDirectory(compilationDirectoryPath.toFile());
        return conf;
    }

    private void createCompilationDirectory(final Path compilationDirectory) throws CompilationException {
        final File compilationDir = new File(compilationDirectory.toUri());
        final boolean wasCreated = compilationDir.mkdirs();

        if (!wasCreated) {
            throw new CompilationException("Could not crete directory");
        }
    }
}
