package lab28.group4.asm1.commands;

import lab28.group4.asm1.Application;
import lab28.group4.asm1.models.CartItem;
import lab28.group4.asm1.models.Order;
import lab28.group4.asm1.repositories.OrderRepository;
import org.springframework.stereotype.Component;
import picocli.CommandLine;


@Component
@CommandLine.Command(name = "order", mixinStandardHelpOptions = true)
public class OrderCommand extends ApplicationCommand {

    private final OrderRepository orderRepository;

    public OrderCommand(OrderRepository orderRepository, CommandLine.IFactory factory) {
        super(factory);
        this.orderRepository = orderRepository;
    }

    @CommandLine.Option(names = "--id", description = "Order ID", required = true)
    private Long orderId;

    @Override
    public Integer call() {
        Order order = orderRepository.findById(orderId).orElse(null);
        if (order == null) {
            Application.LOG.error("Order not found for ID: " + orderId);
            return 1;
        }
        Application.LOG.info("Order Date: " + order.getCreatedAt());
        Application.LOG.info("Order Items:");
        for (CartItem item : order.getItems()) {
            Application.LOG.info("{}x {} (${})", item.getQuantity(), item.getMenuItem().getName(), item.getPrice());
        }
        Application.LOG.info("Total Amount: $" + order.getPrice());

        return 0;
    }

}