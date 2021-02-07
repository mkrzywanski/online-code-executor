package io.mkrzywanski.onlinecodeexecutor.language.kotlin;

import io.mkrzywanski.onlinecodeexecutor.language.compilation.CompilationException;
import io.mkrzywanski.onlinecodeexecutor.language.compilation.CompiledClass;
import io.mkrzywanski.onlinecodeexecutor.language.compilation.Compiler;
import org.apache.commons.io.FileUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.kotlin.cli.common.ExitCode;
import org.jetbrains.kotlin.cli.common.arguments.K2JVMCompilerArguments;
import org.jetbrains.kotlin.cli.common.messages.MessageCollector;
import org.jetbrains.kotlin.cli.jvm.K2JVMCompiler;
import org.jetbrains.kotlin.config.Services;
import org.jetbrains.kotlin.utils.fileUtils.FileUtilsKt;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class KotlinCompiler implements Compiler {

    private final K2JVMCompiler k2JVMCompiler = new K2JVMCompiler();

    private final Path baseDir;

    public KotlinCompiler(Path baseDir) {
        this.baseDir = baseDir;
    }

    @Override
    public Set<CompiledClass> compile(final String code) throws CompilationException {
        K2JVMCompilerArguments compilerArguments = new K2JVMCompilerArguments();

        String executionId = UUID.randomUUID().toString();

        //createDirectory(baseDir.toString());
        String aa = baseDir + "/" + executionId;
        //createDirectory(aa);
        String o = (String) System.getProperties().get("java.class.path");
        String sourcesDir = getDirectoryPath(executionId, "sources");
        String outputDir = getDirectoryPath(executionId, "output");
        createDirectory(sourcesDir);
        //createFile(sourcesDir + "/source.kt");
        createDirectory(outputDir);

        saveToFile(code, sourcesDir);

        compilerArguments.setFreeArgs(List.of(sourcesDir));
        compilerArguments.setDestination(outputDir);
        compilerArguments.setNoStdlib(true);
        compilerArguments.setClasspath(o);

        compile(compilerArguments);

        Set<CompiledClass> compiledClasses = getCompiledClasses(outputDir);

        try {
            FileUtils.deleteDirectory(new File(aa));
        } catch (IOException e) {
            throw new CompilationException(e);
        }

        return compiledClasses;

    }

    @NotNull
    private String getDirectoryPath(String executionId, String sources) {
        return baseDir.toString() + "/" + executionId + "/" + sources;
    }

    private void compile(final K2JVMCompilerArguments compilerArguments) throws CompilationException {
        CustomMessageCollector messageCollector = new CustomMessageCollector();
        ExitCode exitCode = k2JVMCompiler.execImpl(messageCollector, Services.EMPTY, compilerArguments);

        if (exitCode.getCode() != 0) {
            throw new CompilationException("Compilation failed", messageCollector.report());
        }
    }

    @NotNull
    private Set<CompiledClass> getCompiledClasses(String outputDir) throws CompilationException {
        try (Stream<Path> pathStram = Files.list(Paths.get(outputDir))) {
            return pathStram.filter(path -> !path.toFile().isDirectory())
                    .map(this::read)
                    .collect(Collectors.toSet());
        } catch (IOException | UncheckedIOException e) {
            throw new CompilationException(e);
        }
    }

    private CompiledClass read(Path path) {
        try (BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(path.toFile()));) {
            byte[] bytes = bufferedInputStream.readAllBytes();
            String[] fileName = path.getFileName().toString().split("\\.");
            return new CompiledClass(fileName[0], bytes);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private void saveToFile(String code, String directory) throws CompilationException {
        //createFile(s);
        try {
            Files.writeString(Paths.get(directory + "/source.kt"), code);
        } catch (IOException e) {
            throw new CompilationException(e);
        }
    }

    private void createDirectory(String path) throws CompilationException {
        boolean newFile = new File(path).mkdirs();
        if (!newFile) {
            throw new CompilationException("");
        }
    }
}
