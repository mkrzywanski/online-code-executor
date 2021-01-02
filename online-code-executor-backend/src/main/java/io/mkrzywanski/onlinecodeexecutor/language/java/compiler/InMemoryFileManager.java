/*
 * This file is part of Graylog Pipeline Processor.
 *
 * Graylog Pipeline Processor is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Graylog Pipeline Processor is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Graylog Pipeline Processor.  If not, see <http://www.gnu.org/licenses/>.
 */
package io.mkrzywanski.onlinecodeexecutor.language.java.compiler;

import javax.tools.FileObject;
import javax.tools.ForwardingJavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.StandardLocation;
import javax.validation.constraints.NotNull;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class InMemoryFileManager extends ForwardingJavaFileManager<StandardJavaFileManager> {

    private final Map<String, ByteArrayOutputStream> classBytes = new LinkedHashMap<>();

    public InMemoryFileManager(final StandardJavaFileManager fileManager) {
        super(fileManager);
    }

    @Override
    public JavaFileObject getJavaFileForInput(final Location location, final String className, final JavaFileObject.Kind kind) throws IOException {
        if (location == StandardLocation.CLASS_OUTPUT && classBytes.containsKey(className) && kind == JavaFileObject.Kind.CLASS) {
            final byte[] bytes = classBytes.get(className).toByteArray();
            return new SimpleJavaFileObject(URI.create(className), kind) {
                @NotNull
                public InputStream openInputStream() {
                    return new ByteArrayInputStream(bytes);
                }
            };
        }
        return fileManager.getJavaFileForInput(location, className, kind);
    }

    @Override
    public JavaFileObject getJavaFileForOutput(final Location location, final String className, JavaFileObject.Kind kind, FileObject sibling) throws IOException {
        return new SimpleJavaFileObject(URI.create(className), kind) {
            @NotNull
            public OutputStream openOutputStream() {
                final ByteArrayOutputStream stream = new ByteArrayOutputStream();
                classBytes.put(className, stream);
                return stream;
            }
        };
    }

    @NotNull
    public Map<String, byte[]> getCompiledClassBytes() {
        return classBytes.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().toByteArray()));
    }
}
