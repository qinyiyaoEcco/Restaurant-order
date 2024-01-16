package lab28.group4.asm1.commands.cart;

import lab28.group4.asm1.Application;
import lab28.group4.asm1.commands.ApplicationCommand;
import lab28.group4.asm1.models.CartItem;
import lab28.group4.asm1.models.Order;
import lab28.group4.asm1.repositories.CartItemRepository;
import lab28.group4.asm1.repositories.OrderRepository;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

import java.util.List;

@Component
@CommandLine.Command(name = "checkout", mixinStandardHelpOptions = true)
public class CheckoutCommand extends ApplicationCommand {

    private final CartItemRepository cartItemRepository;
    private final OrderRepository orderRepository;

    public CheckoutCommand(OrderRepository orderRepository, CartItemRepository cartItemRepository, CommandLine.IFactory factory) {
        super(factory);
        this.cartItemRepository = cartItemRepository;
        this.orderRepository = orderRepository;
    }

    @CommandLine.Option(names = "--delivery", description = "Select delivery option")
    private boolean isDelivery;

    @CommandLine.Option(names = "--pickup", description = "Select pickup option")
    private boolean isPickup;

    public Integer call() {
        if (isDelivery && isPickup) {
            Application.LOG.error("Please select either delivery or pickup, not both.");
            return 1;
        }
        if (!isDelivery && !isPickup) {
            Application.LOG.error("Please select a delivery or pickup option.");
            return 1;
        }
        List<CartItem> cartItems = cartItemRepository.findCartItemsByOrder(null);
        if (cartItems.isEmpty()) {
            Application.LOG.error("Your cart is empty.");
            return 1;
        }
        Order order = new Order(cartItems, isDelivery);
        orderRepository.save(order);
        for (CartItem cartItem : cartItems) {
            cartItem.setOrder(order);
            cartItemRepository.save(cartItem);
        }

        Application.LOG.info("Order confirmed with ID {}!", order.getId());
        Application.LOG.info("Order Type: {}", isDelivery ? "Delivery" : "Pickup");
        Application.LOG.info("Total Amount: ${}", order.getPrice());

        return 0;
    }
}


