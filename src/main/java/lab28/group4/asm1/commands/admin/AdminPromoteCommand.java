package lab28.group4.asm1.commands.admin;

import lab28.group4.asm1.Application;
import lab28.group4.asm1.Role;
import lab28.group4.asm1.commands.SecuredCommand;
import lab28.group4.asm1.models.User;
import lab28.group4.asm1.repositories.UserRepository;
import org.springframework.stereotype.Component;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.IFactory;

@Component
@Command(name = "admin-promote", mixinStandardHelpOptions = true, description = "Promote a user to an admin")
class AdminPromoteCommand extends SecuredCommand {

    @CommandLine.Option(names = {"--target"}, description = "Target username", required = true)
    private String targetUsername;

    public AdminPromoteCommand(UserRepository userRepository, IFactory factory) {
        super(Role.ADMIN, userRepository, factory);
    }

    @Override
    public Integer secureCall() {
        UserRepository userRepository = getUserRepository();
        User user = userRepository.findByUsername(targetUsername);
        if (user == null) {
            Application.LOG.error("User {} does not exist", targetUsername);
            return 1;
        }
        user.setRole(Role.ADMIN);
        userRepository.save(user);
        Application.LOG.info("User {} has been promoted", targetUsername);
        return 0;
    }

}
