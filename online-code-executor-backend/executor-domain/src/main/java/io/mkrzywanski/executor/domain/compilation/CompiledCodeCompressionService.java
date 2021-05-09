package io.mkrzywanski.executor.domain.compilation;

import io.mkrzywanski.executor.domain.compilation.exception.CompressionFailureException;
import io.mkrzywanski.executor.domain.compilation.model.CompiledClass;
import io.mkrzywanski.executor.domain.compilation.model.CompiledClasses;
import net.lingala.zip4j.io.outputstream.ZipOutputStream;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.model.enums.CompressionMethod;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public final class CompiledCodeCompressionService {

    private static final int BUFFER_SIZE = 1024;

    private CompiledCodeCompressionService() {
    }

    public static CompiledCodeCompressionService newInstance() {
        return new CompiledCodeCompressionService();
    }

    public InputStream compress(final CompiledClasses compiledClasses) throws CompressionFailureException {
        final ZipParameters zipParameters = buildZipParameters();
        final byte[] buff = new byte[BUFFER_SIZE];
        int readLen;
        final ByteArrayOutputStream boas = new ByteArrayOutputStream();
        try (ZipOutputStream zos = new ZipOutputStream(boas)) {
            for (CompiledClass compiledClass : compiledClasses.asSet()) {
                if (zipParameters.getCompressionMethod() == CompressionMethod.STORE) {
                    zipParameters.setEntrySize(compiledClass.getBytes().length);
                }

                zipParameters.setFileNameInZip(compiledClass.getName());
                zos.putNextEntry(zipParameters);

                try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(compiledClass.getBytes())) {
                    while ((readLen = byteArrayInputStream.read(buff)) != -1) {
                        zos.write(buff, 0, readLen);
                    }
                }
                zos.closeEntry();
            }
        } catch (final IOException e) {
            throw new CompressionFailureException(e);
        }
        return new ByteArrayInputStream(boas.toByteArray());
    }

    private ZipParameters buildZipParameters() {
        final ZipParameters zipParameters = new ZipParameters();
        zipParameters.setCompressionMethod(CompressionMethod.STORE);
        return zipParameters;
    }
}
