package lab28.group4.asm1.commands.shell;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import org.fusesource.jansi.Ansi;

import java.util.concurrent.LinkedBlockingQueue;

public class ShellAppender extends AppenderBase<ILoggingEvent> {
    private static final LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();

    private Ansi.Color getColorForLevel(Level level) {
        switch (level.levelInt) {
            case Level.ERROR_INT -> {
                return Ansi.Color.RED;
            }
            case Level.WARN_INT -> {
                return Ansi.Color.YELLOW;
            }
            case Level.INFO_INT -> {
                return Ansi.Color.GREEN;
            }
            case Level.DEBUG_INT -> {
                return Ansi.Color.BLUE;
            }
            default -> {
                return Ansi.Color.WHITE;
            }
        }
    }

    @Override
    protected void append(ILoggingEvent event) {
        String levelColor = Ansi.ansi().fg(getColorForLevel(event.getLevel())).toString();
        String resetColor = Ansi.ansi().reset().toString();
        String message = String.format("%s[%s] %s%s", levelColor, event.getLevel().levelStr, resetColor, event.getFormattedMessage());
        queue.offer(message);
    }

    public static LinkedBlockingQueue<String> getLogQueue() {
        return queue;
    }
}
