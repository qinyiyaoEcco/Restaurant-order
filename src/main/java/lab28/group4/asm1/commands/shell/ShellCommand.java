package lab28.group4.asm1.commands.shell;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import lab28.group4.asm1.Application;
import lab28.group4.asm1.commands.ApplicationCommand;
import lab28.group4.asm1.commands.shell.session.Session;
import org.fusesource.jansi.AnsiConsole;
import org.jline.console.SystemRegistry;
import org.jline.console.impl.SystemRegistryImpl;
import org.jline.reader.*;
import org.jline.reader.impl.DefaultParser;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.jline.widget.TailTipWidgets;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import picocli.CommandLine;
import picocli.shell.jline3.PicocliCommands;

import java.util.concurrent.LinkedBlockingQueue;

@Component
@CommandLine.Command(name = "shell", mixinStandardHelpOptions = true)
@ShellExclude
public class ShellCommand extends ApplicationCommand {

    public ShellCommand(CommandLine.IFactory factory) {
        super(factory);
    }

    private ShellRootCommand rootCommand;

    @Autowired
    @Lazy
    public void setRootCommand(ShellRootCommand rootCommand) {
        this.rootCommand = rootCommand;
    }

    @CommandLine.Option(names = {"--non-interactive"}, description = "Disable interactive mode")
    private boolean nonInteractive;

    @Override
    public Integer call() {
        AnsiConsole.systemInstall();
        PicocliCommands.PicocliCommandsFactory factory = new PicocliCommands.PicocliCommandsFactory();
        CommandLine cmd = new CommandLine(rootCommand, factory);
        rootCommand.getCommandLine().getSubcommands().forEach(cmd::addSubcommand);
        PicocliCommands commands = new PicocliCommands(cmd);
        Parser parser = new DefaultParser();
        try (Terminal terminal = nonInteractive ? TerminalBuilder.builder().streams(System.in, System.out).build() : TerminalBuilder.builder().build()) {
            SystemRegistry systemRegistry = new SystemRegistryImpl(parser, terminal, null, null);
            systemRegistry.setCommandRegistries(commands);
            systemRegistry.register("help", commands);
            LineReader reader = LineReaderBuilder.builder().terminal(terminal).completer(systemRegistry.completer()).parser(parser).variable(LineReader.LIST_MAX, 50).build();
            rootCommand.setReader(reader);
            factory.setTerminal(terminal);
            TailTipWidgets widgets = new TailTipWidgets(reader, systemRegistry::commandDescription, 5, TailTipWidgets.TipType.COMPLETER);
            widgets.enable();
            configureLogger();
            Application.LOG.info("Welcome to the shell. Type 'help' to list commands.");

            String line;
            while (true) {
                printPendingLogs(reader);
                String username = Session.getUsername();
                String prompt = String.format("%s > ", username == null ? "guest" : username);
                try {
                    systemRegistry.cleanUp();
                    line = reader.readLine(prompt);
                    ParsedLine pl = reader.getParser().parse(line, 0);
                    String[] arguments = pl.words().toArray(new String[0]);
                    if (arguments.length > 0 && rootCommand.isSecured(arguments[0]) && username != null && !hasCredentials(line)) {
                        line += String.format(" -u %s -p %s", username, Session.getPassword());
                    }
                    systemRegistry.execute(line);
                } catch (UserInterruptException ignored) {
                } catch (EndOfFileException e) {
                    return 0;
                } catch (Exception e) {
                    systemRegistry.trace(e);
                }
            }
        } catch (Throwable t) {
            Application.LOG.error("Error while running shell", t);
            return 1;
        } finally {
            AnsiConsole.systemUninstall();
        }
    }

    private boolean hasCredentials(String line) {
        return hasFlag(line, "-u") || hasFlag(line, "--username") || hasFlag(line, "-p") || hasFlag(line, "--password");
    }

    private boolean hasFlag(String line, String flag) {
        return line.contains(" " + flag + " ") || line.contains(" " + flag + "=");
    }

    private void configureLogger() {
        Logger rootLogger = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        LoggerContext loggerContext = rootLogger.getLoggerContext();
        rootLogger.detachAndStopAllAppenders();
        ShellAppender shellAppender = new ShellAppender();
        shellAppender.setContext(loggerContext);
        shellAppender.start();
        rootLogger.addAppender(shellAppender);
    }

    private void printPendingLogs(LineReader reader) {
        LinkedBlockingQueue<String> queue = ShellAppender.getLogQueue();
        while (!queue.isEmpty()) {
            reader.printAbove(queue.poll());
        }
    }
}
