package lab28.group4.asm1.commands.cart;

import lab28.group4.asm1.Application;
import lab28.group4.asm1.commands.ApplicationCommand;
import lab28.group4.asm1.models.CartItem;
import lab28.group4.asm1.repositories.CartItemRepository;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

@Component
@CommandLine.Command(name = "cart-adjust", mixinStandardHelpOptions = true)
public class CartAdjustCommand extends ApplicationCommand {

    private final CartItemRepository cartItemRepository;

    public CartAdjustCommand(CartItemRepository cartItemRepository, CommandLine.IFactory factory) {
        super(factory);
        this.cartItemRepository = cartItemRepository;
    }

    @CommandLine.Option(names = "--id", description = "ID of the cart item to adjust", required = true)
    private Long cartItemId;

    @CommandLine.Option(names = "--quantity", description = "New quantity for the item", required = true)
    private int newQuantity;

    public Integer call() {
        CartItem cartItem = cartItemRepository.findById(cartItemId).orElse(null);
        if (cartItem == null) {
            Application.LOG.error("Cart item with ID {} not found", cartItemId);
            return 1;
        }
        cartItem.setQuantity(newQuantity);
        cartItemRepository.save(cartItem);
        Application.LOG.info("Quantity of cart item {} adjusted to {}", cartItem.getMenuItem().getName(), newQuantity);
        return 0;
    }
}

