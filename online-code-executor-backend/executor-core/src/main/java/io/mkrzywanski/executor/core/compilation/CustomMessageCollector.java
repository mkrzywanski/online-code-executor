package io.mkrzywanski.executor.core.compilation;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSeverity;
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSourceLocation;
import org.jetbrains.kotlin.cli.common.messages.MessageCollector;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CustomMessageCollector implements MessageCollector {

    private final List<Message> messages = new ArrayList<>();

    @Override
    public void clear() {
        messages.clear();
    }

    @Override
    public boolean hasErrors() {
        return messages.stream()
                .anyMatch(Message::isErrorMessage);
    }

    @Override
    public void report(@NotNull final CompilerMessageSeverity compilerMessageSeverity,
                       @NotNull final String message,
                       @Nullable final CompilerMessageSourceLocation compilerMessageSourceLocation) {
        messages.add(new Message(compilerMessageSeverity, message, compilerMessageSourceLocation));
    }

    public String report() {
        return messages.stream()
                .map(Message::toString)
                .collect(Collectors.joining(System.lineSeparator()));
    }

    private static class Message {

        private final CompilerMessageSeverity severity;
        private final String message;
        private final CompilerMessageSourceLocation messageSourceLocation;

        private Message(final CompilerMessageSeverity severity, final String message, final CompilerMessageSourceLocation messageSourceLocation) {
            this.severity = severity;
            this.message = message;
            this.messageSourceLocation = messageSourceLocation;
        }

        public CompilerMessageSeverity getSeverity() {
            return severity;
        }

        public String getMessage() {
            return message;
        }

        public CompilerMessageSourceLocation getMessageSourceLocation() {
            return messageSourceLocation;
        }

        public boolean isErrorMessage() {
            return this.severity == CompilerMessageSeverity.ERROR;
        }

        @Override
        public String toString() {
            return "Message{" +
                    "severity=" + severity +
                    ", message='" + message + '\'' +
                    ", messageSourceLocation=" + messageSourceLocation +
                    '}';
        }
    }
}
