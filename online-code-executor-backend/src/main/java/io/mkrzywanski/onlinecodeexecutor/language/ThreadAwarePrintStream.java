package io.mkrzywanski.onlinecodeexecutor.language;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Optional;

public class ThreadAwarePrintStream extends PrintStream {

    private final ThreadLocal<PrintStreamData> printStreamDataTL = ThreadLocal.withInitial(PrintStreamData::newInstance);

    public ThreadAwarePrintStream(final PrintStream out) {
        super(out);
    }

    @Override
    public void println(final String x) {
        PrintStreamData printStreamData = printStreamDataTL.get();
        printStreamData.printStream.println(x);
        super.println(x);
    }

    public String getOutputForCurrentThread() {
        return Optional.ofNullable(printStreamDataTL.get())
                .orElseThrow(RuntimeException::new)
                .outputStream.toString();
    }

    public void removeForCurrentThread() {
        printStreamDataTL.remove();
    }

    private static class PrintStreamData {
        private final PrintStream printStream;
        private final OutputStream outputStream;

        private static PrintStreamData newInstance() {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            return new PrintStreamData(new PrintStream(byteArrayOutputStream), byteArrayOutputStream);
        }

        public PrintStreamData(PrintStream printStream, OutputStream outputStream) {
            this.printStream = printStream;
            this.outputStream = outputStream;
        }
    }
}
