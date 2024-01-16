package lab28.group4.asm1.commands.cart;

import lab28.group4.asm1.Application;
import lab28.group4.asm1.commands.ApplicationCommand;
import lab28.group4.asm1.models.CartItem;
import lab28.group4.asm1.repositories.CartItemRepository;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

@Component
@CommandLine.Command(name = "cart-remove", mixinStandardHelpOptions = true)
public class CartRemoveCommand extends ApplicationCommand {

    private final CartItemRepository cartItemRepository;

    public CartRemoveCommand(CartItemRepository cartItemRepository, CommandLine.IFactory factory) {
        super(factory);
        this.cartItemRepository = cartItemRepository;
    }

    @CommandLine.Option(names = "--id", description = "ID of the cart item to remove", required = true)
    private Long cartItemId;

    public Integer call() {
        CartItem cartItem = cartItemRepository.findById(cartItemId).orElse(null);
        if (cartItem == null) {
            Application.LOG.error("Cart item with ID {} not found", cartItemId);
            return 1;
        }
        cartItemRepository.delete(cartItem);
        Application.LOG.info("Cart item {} removed from the cart.", cartItem.getMenuItem().getName());
        return 0;
    }
}
