package lab28.group4.asm1.commands.shell.session;

import lab28.group4.asm1.Application;
import lab28.group4.asm1.commands.SecuredCommand;
import lab28.group4.asm1.repositories.UserRepository;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

@Component
@CommandLine.Command(name = "login", mixinStandardHelpOptions = true)
public class LoginCommand extends SecuredCommand {

    public LoginCommand(UserRepository userRepository, CommandLine.IFactory factory) {
        super(userRepository, factory);
    }

    @Override
    public Integer secureCall() {
        String username = getUsername();
        Session.setSession(username, getPassword());
        Application.LOG.info("Hello {}! You are logged in!", username);
        return 0;
    }

}
