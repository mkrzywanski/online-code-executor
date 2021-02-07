package io.mkrzywanski.onlinecodeexecutor.language.interceptor;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;

public class ThreadOutputPrintStreamInterceptor implements InvocationHandler, ThreadOutputInterceptor {

    private final PrintStream proxied;

    private final ThreadLocal<PrintStreamData> printStreamDataTL = ThreadLocal.withInitial(PrintStreamData::newInstance);

    public ThreadOutputPrintStreamInterceptor(PrintStream proxied) {
        this.proxied = proxied;
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
    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
        PrintStreamData printStreamData = printStreamDataTL.get();
        PrintStream printStream = printStreamData.printStream;

        Class<? extends PrintStream> aClass = printStream.getClass();

        Method method1 = aClass.getMethod(method.getName(), Arrays.stream(objects).map(Object::getClass).toArray(Class[]::new));
        method1.invoke(printStream, objects);
        return method1.invoke(proxied, objects);
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
