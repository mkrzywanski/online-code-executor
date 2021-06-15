package io.mkrzywanski.executor.core.compilation;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.kotlin.cli.common.ExitCode;
import org.jetbrains.kotlin.cli.common.arguments.K2JVMCompilerArguments;
import org.jetbrains.kotlin.cli.jvm.K2JVMCompiler;
import org.jetbrains.kotlin.config.Services;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class KotlinCompiler implements Compiler {

    private final K2JVMCompiler k2JVMCompiler;
    private final Path baseDir;
    private final FileOperations fileOperations;

    KotlinCompiler(final Path baseDir, final FileOperations fileOperations) {
        this.k2JVMCompiler =  new K2JVMCompiler();
        this.baseDir = baseDir;
        this.fileOperations = fileOperations;
    }

    @Override
    public Set<CompiledClass> compile(final String code) throws CompilationException {
        final K2JVMCompilerArguments compilerArguments = new K2JVMCompilerArguments();

        final String executionId = CompilationId.generate().asString();

        final String executionDirectory = baseDir + "/" + executionId;
        final String classPath = (String) System.getProperties().get("java.class.path");
        final String sourcesDir = getDirectoryPath(executionId, "sources");
        final String outputDir = getDirectoryPath(executionId, "output");

        createDirectory(sourcesDir);
        createDirectory(outputDir);

        saveToFile(code, sourcesDir);

        compilerArguments.setFreeArgs(List.of(sourcesDir));
        compilerArguments.setDestination(outputDir);
        compilerArguments.setNoStdlib(true);
        compilerArguments.setClasspath(classPath);

        compile(compilerArguments);

        final Set<CompiledClass> compiledClasses = getCompiledClasses(outputDir);

        deleteDirectory(executionDirectory);

        return compiledClasses;

    }

    private void compile(final K2JVMCompilerArguments compilerArguments) throws CompilationException {
        final CustomMessageCollector messageCollector = new CustomMessageCollector();
        final ExitCode exitCode = k2JVMCompiler.execImpl(messageCollector, Services.EMPTY, compilerArguments);

        if (exitCode.getCode() != 0) {
            throw new CompilationException("Compilation failed", messageCollector.report());
        }
    }

    private void deleteDirectory(final String path) throws CompilationException {
        try {
            fileOperations.deleteDir(Path.of(path));
        } catch (final IOException e) {
            throw new CompilationException(e);
        }
    }

    @NotNull
    private String getDirectoryPath(final String executionId, final String sources) {
        return baseDir.toString() + "/" + executionId + "/" + sources;
    }

    @NotNull
    private Set<CompiledClass> getCompiledClasses(final String outputDirectoryPath) throws CompilationException {
        try (Stream<Path> pathStream = Files.list(Paths.get(outputDirectoryPath))) {
            return pathStream.filter(path -> !path.toFile().isDirectory())
                    .map(this::read)
                    .collect(Collectors.toSet());
        } catch (IOException | UncheckedIOException e) {
            throw new CompilationException(e);
        }
    }

    private CompiledClass read(final Path path) {
        try (BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(path.toFile()));) {
            final byte[] bytes = bufferedInputStream.readAllBytes();
            final String[] fileNameParts = path.getFileName().toString().split("\\.");
            return new CompiledClass(fileNameParts[0], bytes);
        } catch (final IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private void saveToFile(final String code, final String directory) throws CompilationException {
        try {
            Files.writeString(Paths.get(directory + "/source.kt"), code);
        } catch (final IOException e) {
            throw new CompilationException(e);
        }
    }

    private void createDirectory(final String path) throws CompilationException {
        final boolean wasCreated = new File(path).mkdirs();
        if (!wasCreated) {
            throw new CompilationException(String.format("Could not create directory %s", path));
        }
    }
}
