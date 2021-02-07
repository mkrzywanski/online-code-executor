package io.mkrzywanski.onlinecodeexecutor.language.groovy;

import groovy.lang.GroovyClassLoader;
import io.mkrzywanski.onlinecodeexecutor.language.compilation.CompilationException;
import io.mkrzywanski.onlinecodeexecutor.language.compilation.CompiledClass;
import io.mkrzywanski.onlinecodeexecutor.language.compilation.Compiler;
import org.codehaus.groovy.control.CompilationUnit;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.codehaus.groovy.tools.GroovyClass;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GroovyCompiler implements Compiler {

    private final Path compilationBaseDirectory;

    public GroovyCompiler(final Path compilationBaseDirectory) {
        this.compilationBaseDirectory = compilationBaseDirectory;
    }

    @Override
    public Set<CompiledClass> compile(String code) throws CompilationException {

        String executionId = UUID.randomUUID().toString();

        Path compilationDirectoryPath = Paths.get(compilationBaseDirectory + "/" + executionId);
        createCompilationDirectory(compilationDirectoryPath);

        final CompilerConfiguration conf = getCompilerConfiguration(compilationDirectoryPath);

        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();

        GroovyClassLoader groovyClassLoader = new GroovyClassLoader(contextClassLoader, conf, false);

        CompilationUnit unit = new CompilationUnit(conf, null, groovyClassLoader);
        unit.addSource("test", code);

        unit.compile();

        List<GroovyClass> classes = unit.getClasses();

        Set<CompiledClass> compiledClasses = classes.stream()
                .map(groovyClass -> new CompiledClass(groovyClass.getName(), groovyClass.getBytes()))
                .collect(Collectors.toSet());

        deleteDirectory(compilationDirectoryPath);
        return compiledClasses;

    }

    private void deleteDirectory(Path compilationDirectoryPath) throws CompilationException {
        try (Stream<Path> walk = Files.walk(compilationDirectoryPath)) {
            walk.sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .forEach(File::delete);
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
