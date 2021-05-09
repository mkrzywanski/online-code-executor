package io.mkrzywanski.executor.core.compilation;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.stream.Stream;

final class FileOperations {

    private FileOperations() {
    }

    static FileOperations create() {
        return new FileOperations();
    }

    void deleteDir(final Path path) throws IOException {
        try (Stream<Path> walk = Files.walk(path)) {
            walk.sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .forEach(File::delete);
        }
    }
}
