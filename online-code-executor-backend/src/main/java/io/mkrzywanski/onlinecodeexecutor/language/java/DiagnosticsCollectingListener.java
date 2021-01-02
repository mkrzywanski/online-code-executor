package io.mkrzywanski.onlinecodeexecutor.language.java;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticListener;
import javax.tools.JavaFileObject;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DiagnosticsCollectingListener implements DiagnosticListener<JavaFileObject> {

    private final List<Diagnostic<? extends JavaFileObject>> diagnostics = new ArrayList<>();

    @Override
    public void report(Diagnostic<? extends JavaFileObject> diagnostic) {
        diagnostics.add(diagnostic);
    }

    public String generateReport() {
        return diagnostics.stream()
                .map(Object::toString)
                .collect(Collectors.joining("\n"));
    }
}
