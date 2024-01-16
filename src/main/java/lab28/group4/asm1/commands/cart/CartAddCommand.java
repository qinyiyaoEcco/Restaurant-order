package lab28.group4.asm1.commands.cart;

import lab28.group4.asm1.Application;
import lab28.group4.asm1.commands.ApplicationCommand;
import lab28.group4.asm1.models.CartItem;
import lab28.group4.asm1.models.MenuItem;
import lab28.group4.asm1.repositories.CartItemRepository;
import lab28.group4.asm1.repositories.MenuItemRepository;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

@Component
@CommandLine.Command(name = "cart-add", mixinStandardHelpOptions = true)
public class CartAddCommand extends ApplicationCommand {

    private final MenuItemRepository menuItemRepository;
    private final CartItemRepository cartItemRepository;

    public CartAddCommand(MenuItemRepository menuItemRepository, CartItemRepository cartItemRepository, CommandLine.IFactory factory) {
        super(factory);
        this.menuItemRepository = menuItemRepository;
        this.cartItemRepository = cartItemRepository;
    }

    @CommandLine.Option(names = "--id", description = "ID of the menu item to add to the cart", required = true)
    private Long menuItemId;

    @CommandLine.Option(names = "--quantity", description = "Quantity of the item to add", required = true)
    private int quantity;

    public Integer call() {
        MenuItem menuItem = menuItemRepository.findById(menuItemId).orElse(null);
        if (menuItem == null) {
            System.err.println("Menu item with ID " + menuItemId + " not found.");
            return 1;
        }
        CartItem cartItem = cartItemRepository.findCartItemByMenuItem(menuItem).orElse(new CartItem(menuItem));
        cartItem.addQuantity(quantity);
        cartItemRepository.save(cartItem);
        Application.LOG.info("{} {} added to the cart.", quantity, menuItem.getName());
        return 0;
    }
}
