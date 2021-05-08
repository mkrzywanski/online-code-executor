package io.mkrzywanski.executor.test.matchers

import net.lingala.zip4j.io.inputstream.ZipInputStream
import org.hamcrest.Description
import org.hamcrest.TypeSafeMatcher

class ContainsNumberOfZippedFiles extends TypeSafeMatcher<InputStream> {

    private int expected;

    static def containsNumberOfZippedFiles(final int expectedFiles) {
        return new ContainsNumberOfZippedFiles(expectedFiles)
    }

    private ContainsNumberOfZippedFiles(final int expected) {
        this.expected = expected
    }

    @Override
    protected boolean matchesSafely(final InputStream item) {
        def localFileHeader
        def actual = 0
        try (ZipInputStream zipInputStream = new ZipInputStream(item)) {
            while ((localFileHeader = zipInputStream.getNextEntry()) != null) {
                readSingleEntry(zipInputStream)
                actual++
            }
        }
        return actual == expected
    }

    @Override
    void describeTo(final Description description) {
        description.appendText("input stream containing ")
    }

    private static byte[] readSingleEntry(ZipInputStream zipInputStream) {
        int readLen
        byte[] readBuffer = new byte[4096]

        OutputStream outputStream = new ByteArrayOutputStream()
        while ((readLen = zipInputStream.read(readBuffer)) != -1) {
            outputStream.write(readBuffer, 0, readLen);
        }
        outputStream.close()
        outputStream.toByteArray()
    }
}