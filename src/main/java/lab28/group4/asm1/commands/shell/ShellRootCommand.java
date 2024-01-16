package lab28.group4.asm1.commands.shell;

import lab28.group4.asm1.commands.ApplicationCommand;
import lab28.group4.asm1.commands.SecuredCommand;
import org.jline.reader.LineReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@CommandLine.Command(name = "", footer = {"", "Press Ctrl-D to exit."})
@ShellExclude
public class ShellRootCommand extends ApplicationCommand {
    PrintWriter out;

    public ShellRootCommand(CommandLine.IFactory factory) {
        super(factory);
    }

    private final Map<String, ApplicationCommand> commandMap = new HashMap<>();

    @Autowired
    @Lazy
    public void setCommands(List<ApplicationCommand> commands) {
        CommandLine commandLine = this.getCommandLine();
        for (ApplicationCommand command : commands) {
            Annotation annotation = command.getClass().getAnnotation(ShellExclude.class);
            if (annotation == null) {
                commandLine.addSubcommand(command);
                this.commandMap.put(command.getName(), command);
            }
        }
    }

    public void setReader(LineReader reader) {
        out = reader.getTerminal().writer();
    }

    @Override
    public Integer call() {
        out.println(this.getCommandLine().getUsageMessage());
        return 0;
    }

    public boolean isSecured(String name) {
        return commandMap.get(name) instanceof SecuredCommand;
    }
}
