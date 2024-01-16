package lab28.group4.asm1.commands;

import lab28.group4.asm1.Application;
import lab28.group4.asm1.commands.shell.session.Session;
import lab28.group4.asm1.models.User;
import lab28.group4.asm1.repositories.UserRepository;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

@Component
@CommandLine.Command(name = "change-password", mixinStandardHelpOptions = true)
public class ChangePasswordCommand extends SecuredCommand {

    public ChangePasswordCommand(UserRepository userRepository, CommandLine.IFactory factory) {
        super(userRepository, factory);
    }

    @CommandLine.Option(names = {"-n", "--new-password"}, description = "New password", required = true)
    private String newPassword;

    @Override
    public Integer secureCall() {
        User user = getUser();
        user.setPassword(newPassword);
        getUserRepository().save(user);
        if (user.getUsername().equals(Session.getUsername())) {
            Session.setSession(user.getUsername(), newPassword);
        }
        Application.LOG.info("Password changed successfully!");
        return 0;
    }

}
