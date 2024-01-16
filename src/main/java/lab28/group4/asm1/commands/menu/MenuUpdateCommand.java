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
@CommandLine.Command(name = "menu-update", mixinStandardHelpOptions = true, description = "Update a menu item")
public class MenuUpdateCommand extends SecuredCommand {

    private final MenuItemRepository menuItemRepository;

    public MenuUpdateCommand(MenuItemRepository menuItemRepository, UserRepository userRepository, CommandLine.IFactory factory) {
        super(Role.ADMIN, userRepository, factory);
        this.menuItemRepository = menuItemRepository;
    }

    @CommandLine.Option(names = "--id", description = "ID of the menu item", required = true)
    private Long id;

    @CommandLine.Option(names = "--name", description = "Name of the menu item")
    private String name;

    @CommandLine.Option(names = "--price", description = "Price of the menu item")
    private Double price;

    @CommandLine.Option(names = "--description", description = "Description of the menu item")
    private String description;

    @Override
    public Integer secureCall() {
        MenuItem menuItem = menuItemRepository.findById(id).orElse(null);
        if (menuItem == null) {
            Application.LOG.error("Menu item with ID {} not found!", id);
            return 1;
        }
        if (name != null) {
            menuItem.setName(name);
        }
        if (price != null) {
            menuItem.setPrice(price);
        }
        if (description != null) {
            menuItem.setDescription(description);
        }
        menuItemRepository.save(menuItem);
        Application.LOG.info("Menu item updated successfully!");
        return 0;
    }

}
