package lab28.group4.asm1.commands.admin;

import lab28.group4.asm1.Application;
import lab28.group4.asm1.Role;
import lab28.group4.asm1.commands.SecuredCommand;
import lab28.group4.asm1.repositories.OrderRepository;
import lab28.group4.asm1.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import picocli.CommandLine.Command;
import picocli.CommandLine.IFactory;

import java.util.ArrayList;
import java.util.List;

@Component
@Command(name = "admin", mixinStandardHelpOptions = true)
class AdminCommand extends SecuredCommand {

    private final OrderRepository orderRepository;
    private List<SecuredCommand> commands;

    @Autowired
    @Lazy
    public void setCommands(List<SecuredCommand> commands) {
        this.commands = commands.stream().filter(command -> command.getRole().ordinal() >= Role.ADMIN.ordinal()).collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }

    public AdminCommand(OrderRepository orderRepository, UserRepository userRepository, IFactory factory) {
        super(Role.ADMIN, userRepository, factory);
        this.orderRepository = orderRepository;
    }

    @Override
    public Integer secureCall() {
        Application.LOG.info("Hello {}!", getUser().getUsername());
        Application.LOG.info("Total Orders Processed: {}", orderRepository.count());
        Application.LOG.info("Available Admin Commands:");
        for (SecuredCommand command : commands) {
            Application.LOG.info("{}: {}", command.getName(), command.getCommandLine().getCommandSpec().usageMessage().description()[0]);
        }
        return 0;
    }

}