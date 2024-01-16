package lab28.group4.asm1.commands.menu;

import lab28.group4.asm1.Application;
import lab28.group4.asm1.Role;
import lab28.group4.asm1.commands.SecuredCommand;
import lab28.group4.asm1.models.MenuItem;
import lab28.group4.asm1.repositories.MenuItemRepository;
import lab28.group4.asm1.repositories.UserRepository;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

@Component
@CommandLine.Command(name = "menu-remove", mixinStandardHelpOptions = true, description = "Remove a menu item")
public class MenuRemoveCommand extends SecuredCommand {

    private final MenuItemRepository menuItemRepository;

    public MenuRemoveCommand(MenuItemRepository menuItemRepository, UserRepository userRepository, CommandLine.IFactory factory) {
        super(Role.ADMIN, userRepository, factory);
        this.menuItemRepository = menuItemRepository;
    }

    @CommandLine.Option(names = "--id", description = "ID of the menu item", required = true)
    private Long id;

    @Override
    public Integer secureCall() {
        MenuItem menuItem = menuItemRepository.findById(id).orElse(null);
        if (menuItem == null) {
            Application.LOG.error("Menu item with ID {} not found!", id);
            return 1;
        }
        menuItemRepository.delete(menuItem);
        Application.LOG.info("Menu item removed successfully!");
        return 0;
    }
}
