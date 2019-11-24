package core.logging;

import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;

public class ThreadLocalLogAppender extends AppenderBase<ILoggingEvent> {
    private static final ThreadLocal<StringBuilder> LOG_BUFFER = new ThreadLocal<>();
    private PatternLayoutEncoder encoder;

    @Override
    protected void append(ILoggingEvent event) {
        if (LOG_BUFFER.get() == null) LOG_BUFFER.set(new StringBuilder());
        LOG_BUFFER.get().append(new String(encoder.encode(event)));
    }

    static boolean isCurrentTreadLogPresented() {
        return LOG_BUFFER.get() != null;
    }

    static byte[] getCurrentThreadLogAsBytes() {
        return getCurrentThreadLogAsString().getBytes();
    }

    private static String getCurrentThreadLogAsString() {
        if (LOG_BUFFER.get() == null) return "";
        return LOG_BUFFER.get().toString();
    }

    static void clearCurrentThreadLog() {
        LOG_BUFFER.remove();
    }

    public void setEncoder(PatternLayoutEncoder encoder) {
        this.encoder = encoder;
    }

    public static void setLayout(PatternLayoutEncoder layout) {
        // Need just for config from xml...
    }
}
