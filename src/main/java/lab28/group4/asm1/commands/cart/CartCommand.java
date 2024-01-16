package lab28.group4.asm1.commands.cart;

import lab28.group4.asm1.Application;
import lab28.group4.asm1.commands.ApplicationCommand;
import lab28.group4.asm1.models.CartItem;
import lab28.group4.asm1.models.MenuItem;
import lab28.group4.asm1.repositories.CartItemRepository;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

import java.util.List;

@Component
@CommandLine.Command(name = "cart", mixinStandardHelpOptions = true)
public class CartCommand extends ApplicationCommand {

    private final CartItemRepository cartItemRepository;

    public CartCommand(CartItemRepository cartItemRepository, CommandLine.IFactory factory) {
        super(factory);
        this.cartItemRepository = cartItemRepository;
    }

    public Integer call() {
        List<CartItem> cartItems = cartItemRepository.findCartItemsByOrder(null);
        if (cartItems.isEmpty()) {
            Application.LOG.info("Your cart is empty.");
            return 0;
        }
        Application.LOG.info("Items in your cart:");
        for (CartItem cartItem : cartItems) {
            MenuItem menuItem = cartItem.getMenuItem();
            Application.LOG.info("{} (Quantity: {})", menuItem.getName(), cartItem.getQuantity());
        }
        return 0;
    }
}
