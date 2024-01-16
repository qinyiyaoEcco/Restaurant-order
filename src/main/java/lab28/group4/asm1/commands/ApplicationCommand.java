package lab28.group4.asm1.commands;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.ExitCodeGenerator;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.IFactory;

import java.util.Arrays;
import java.util.concurrent.Callable;

public abstract class ApplicationCommand implements CommandLineRunner, ExitCodeGenerator, Callable<Integer> {
    private final CommandLine commandLine;
    private final String name;
    private int exitCode;

    public ApplicationCommand(IFactory factory) {
        this.commandLine = new CommandLine(this, factory);
        Command annotation = this.getClass().getAnnotation(Command.class);
        this.name = (annotation != null) ? annotation.name() : null;
    }

    @Override
    public void run(String... args) {
        String command = (args.length > 0) ? args[0] : null;
        if (this.name != null && !this.name.isBlank() && this.name.equals(command)) {
            exitCode = commandLine.execute(Arrays.copyOfRange(args, 1, args.length));
        }
    }

    @Override
    public int getExitCode() {
        return exitCode;
    }

    public CommandLine getCommandLine() {
        return commandLine;
    }

    public String getName() {
        return name;
    }
}
