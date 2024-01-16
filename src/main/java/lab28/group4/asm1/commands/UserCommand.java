package lab28.group4.asm1.commands;

import lab28.group4.asm1.Application;
import lab28.group4.asm1.repositories.UserRepository;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

@Component
@CommandLine.Command(name = "user", mixinStandardHelpOptions = true)
public class UserCommand extends SecuredCommand {

    public UserCommand(UserRepository userRepository, CommandLine.IFactory factory) {
        super(userRepository, factory);
    }

    @Override
    public Integer secureCall() {
        Application.LOG.info("Hello {}! You are a user!", getUser().getUsername());
        return 0;
    }

}
