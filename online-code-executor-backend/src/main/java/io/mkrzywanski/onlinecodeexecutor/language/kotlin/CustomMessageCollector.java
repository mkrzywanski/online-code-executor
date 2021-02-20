package io.mkrzywanski.onlinecodeexecutor.language.kotlin;

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
        return messages.stream().anyMatch(message -> message.severity == CompilerMessageSeverity.ERROR);
    }

    @Override
    public void report(@NotNull CompilerMessageSeverity compilerMessageSeverity, @NotNull String s, @Nullable CompilerMessageSourceLocation compilerMessageSourceLocation) {
        messages.add(new Message(compilerMessageSeverity, s, compilerMessageSourceLocation));
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

        private Message(CompilerMessageSeverity severity, String message, CompilerMessageSourceLocation messageSourceLocation) {
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
