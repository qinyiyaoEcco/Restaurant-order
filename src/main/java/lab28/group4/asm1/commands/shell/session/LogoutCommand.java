package lab28.group4.asm1.commands.shell.session;

import lab28.group4.asm1.Application;
import lab28.group4.asm1.commands.ApplicationCommand;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

@Component
@CommandLine.Command(name = "logout", mixinStandardHelpOptions = true)
public class LogoutCommand extends ApplicationCommand {

    public LogoutCommand(CommandLine.IFactory factory) {
        super(factory);
    }

    @Override
    public Integer call() {
        Session.clearSession();
        Application.LOG.info("You are logged out!");
        return 0;
    }

}
