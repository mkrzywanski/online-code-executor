package io.mkrzywanski.executor.core.compilation;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticListener;
import javax.tools.JavaFileObject;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

class DiagnosticsCollectingListener implements DiagnosticListener<JavaFileObject> {

    private final List<Diagnostic<? extends JavaFileObject>> diagnostics = new ArrayList<>();

    @Override
    public void report(final Diagnostic<? extends JavaFileObject> diagnostic) {
        this.diagnostics.add(diagnostic);
    }

    String generateReport() {
        return this.diagnostics.stream()
                .map(Object::toString)
                .collect(Collectors.joining(System.lineSeparator()));
    }
}
