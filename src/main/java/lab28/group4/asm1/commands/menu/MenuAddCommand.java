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
@CommandLine.Command(name = "menu-add", mixinStandardHelpOptions = true, description = "Add a menu item")
public class MenuAddCommand extends SecuredCommand {

    private final MenuItemRepository menuItemRepository;

    public MenuAddCommand(MenuItemRepository menuItemRepository, UserRepository userRepository, CommandLine.IFactory factory) {
        super(Role.ADMIN, userRepository, factory);
        this.menuItemRepository = menuItemRepository;
    }

    @CommandLine.Option(names = "--name", description = "Name of the menu item", required = true)
    private String name;

    @CommandLine.Option(names = "--price", description = "Price of the menu item", required = true)
    private Double price;

    @CommandLine.Option(names = "--description", description = "Description of the menu item", required = true)
    private String description;

    @Override
    public Integer secureCall() {
        MenuItem menuItem = new MenuItem(name, description, price);
        menuItemRepository.save(menuItem);
        Application.LOG.info("Menu item added successfully with ID {}!", menuItem.getId());
        return 0;
    }

}
