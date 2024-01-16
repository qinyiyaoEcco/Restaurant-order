package lab28.group4.asm1.commands;

import lab28.group4.asm1.Application;
import lab28.group4.asm1.Role;
import lab28.group4.asm1.models.User;
import lab28.group4.asm1.repositories.UserRepository;
import picocli.CommandLine;

public abstract class SecuredCommand extends ApplicationCommand {

    private final Role role;
    private final UserRepository userRepository;

    @CommandLine.Option(names = {"-u", "--username"}, description = "Admin username", required = true)
    private String username;

    @CommandLine.Option(names = {"-p", "--password"}, description = "Admin password", required = true)
    private String password;

    private User user;

    public SecuredCommand(Role role, UserRepository userRepository, CommandLine.IFactory factory) {
        super(factory);
        this.role = role;
        this.userRepository = userRepository;
    }

    public SecuredCommand(UserRepository userRepository, CommandLine.IFactory factory) {
        super(factory);
        this.role = Role.USER;
        this.userRepository = userRepository;
    }

    abstract public Integer secureCall();

    @Override
    public Integer call() {
        User user = userRepository.findByUsername(username);
        if (user == null || !user.validatePassword(password) || user.getRole().ordinal() < role.ordinal()) {
            Application.LOG.error("Invalid credentials");
            return 1;
        }
        this.user = user;
        return secureCall();
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public User getUser() {
        return user;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }
}
