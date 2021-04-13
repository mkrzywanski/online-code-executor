package io.mkrzywanski.onlinecodeexecutor.language.file;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.stream.Stream;

public final class FileOperations {

    private FileOperations() {
    }

    public static FileOperations create() {
        return new FileOperations();
    }

    public void createFile(final Path path) throws IOException {
        File compilationDir = new File(path.toUri());
        boolean mkdirs = compilationDir.mkdirs();

        if(!mkdirs) {
            throw new IOException("Could not crete directory");
        }
    }

    public void deleteDir(final Path path) throws IOException {
        try (Stream<Path> walk = Files.walk(path)) {
            walk.sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .forEach(File::delete);
        }
    }
}
