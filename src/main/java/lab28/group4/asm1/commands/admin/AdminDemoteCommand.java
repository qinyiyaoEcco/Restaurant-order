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
@Command(name = "admin-demote", mixinStandardHelpOptions = true, description = "Demote an admin to a user")
class AdminDemoteCommand extends SecuredCommand {

    @CommandLine.Option(names = {"--target"}, description = "Target username", required = true)
    private String targetUsername;

    public AdminDemoteCommand(UserRepository userRepository, IFactory factory) {
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
        if (userRepository.findByRole(Role.ADMIN).size() == 1) {
            Application.LOG.error("Cannot demote the last admin");
            return 1;
        }
        user.setRole(Role.USER);
        userRepository.save(user);
        Application.LOG.info("User {} has been demoted", targetUsername);
        return 0;
    }

}
