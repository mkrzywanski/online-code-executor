package io.mkrzywanski.executor.core.interceptor;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Optional;

class ThreadOutputPrintStreamInterceptor implements InvocationHandler, ThreadOutputInterceptor {

    private final PrintStream target;

    private final ThreadLocal<PrintStreamData> printStreamDataTL = ThreadLocal.withInitial(PrintStreamData::newInstance);

    ThreadOutputPrintStreamInterceptor(final PrintStream target) {
        this.target = target;
    }

    @Override
    public String getOutputForCurrentThread() {
        return Optional.ofNullable(printStreamDataTL.get())
                .orElseThrow(RuntimeException::new)
                .outputStream.toString();
    }

    @Override
    public void removeForCurrentThread() {
        printStreamDataTL.remove();
    }

    @Override
    public Object invoke(final Object o, final Method targetMethod, final Object[] objects) throws Throwable {
        final PrintStreamData printStreamData = printStreamDataTL.get();
        final PrintStream printStream = printStreamData.printStream;

        targetMethod.invoke(printStream, objects);

        return targetMethod.invoke(target, objects);
    }


    private static class PrintStreamData {
        private final PrintStream printStream;
        private final OutputStream outputStream;

        private PrintStreamData(final PrintStream printStream, final OutputStream outputStream) {
            this.printStream = printStream;
            this.outputStream = outputStream;
        }

        private static PrintStreamData newInstance() {
            final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            return new PrintStreamData(new PrintStream(byteArrayOutputStream), byteArrayOutputStream);
        }
    }
}
