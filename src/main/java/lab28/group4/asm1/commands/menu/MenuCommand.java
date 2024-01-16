package lab28.group4.asm1.commands.menu;

import lab28.group4.asm1.Application;
import lab28.group4.asm1.commands.ApplicationCommand;
import lab28.group4.asm1.models.MenuItem;
import lab28.group4.asm1.repositories.MenuItemRepository;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

import java.util.List;

@Component
@CommandLine.Command(name = "menu", mixinStandardHelpOptions = true)
public class MenuCommand extends ApplicationCommand {

    private final MenuItemRepository menuItemRepository;

    public MenuCommand(MenuItemRepository menuItemRepository, CommandLine.IFactory factory) {
        super(factory);
        this.menuItemRepository = menuItemRepository;
    }

    @Override
    public Integer call() {
        List<MenuItem> menuItems = menuItemRepository.findAll();
        if (menuItems.isEmpty()) {
            Application.LOG.error("No menu items found! Consider adding some with the menu-add command!");
            return 1;
        }
        Application.LOG.info("Menu items:");
        for (MenuItem menuItem : menuItems) {
            Application.LOG.info("ID: {}; Name: {}; Price: {}; Description: {}", menuItem.getId(), menuItem.getName(), menuItem.getPrice(), menuItem.getDescription());
        }
        return 0;
    }

}
