package lab28.group4.asm1.commands;

import lab28.group4.asm1.Application;
import lab28.group4.asm1.Role;
import lab28.group4.asm1.models.User;
import lab28.group4.asm1.repositories.UserRepository;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

@Component
@CommandLine.Command(name = "register", mixinStandardHelpOptions = true)
public class RegisterCommand extends SecuredCommand {

    public RegisterCommand(UserRepository userRepository, CommandLine.IFactory factory) {
        super(userRepository, factory);
    }

    @Override
    public Integer call() {
        String username = getUsername();
        String password = getPassword();
        UserRepository userRepository = getUserRepository();
        if (userRepository.findByUsername(username) != null) {
            Application.LOG.error("Username already exists");
            return 1;
        }
        User user = new User(username, password, Role.USER);
        userRepository.save(user);
        return secureCall();
    }

    @Override
    public Integer secureCall() {
        Application.LOG.info("User registered successfully");
        return 0;
    }
}
