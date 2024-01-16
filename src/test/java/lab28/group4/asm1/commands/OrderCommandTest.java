package lab28.group4.asm1.commands;


import lab28.group4.asm1.models.CartItem;
import lab28.group4.asm1.models.MenuItem;
import lab28.group4.asm1.models.Order;
import lab28.group4.asm1.repositories.CartItemRepository;
import lab28.group4.asm1.repositories.MenuItemRepository;
import lab28.group4.asm1.repositories.OrderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.ArrayList;

@SpringBootTest
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class OrderCommandTest {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderCommand orderCommand;

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Test
    void orderNonExist() {
        String[] args = {"order", "--id", "1"};
        orderCommand.run(args);
        Assertions.assertEquals(1, orderCommand.getExitCode());
    }

    @Test
    void order() {
        MenuItem menuItem = new MenuItem("name", "description", 1);
        menuItemRepository.save(menuItem);
        Order order = new Order(new ArrayList<>(), true);
        orderRepository.save(order);
        CartItem cartItem = new CartItem(menuItem);
        cartItem.setOrder(order);
        cartItemRepository.save(cartItem);
        String[] args = {"order", "--id", "1"};
        orderCommand.run(args);
        Assertions.assertEquals(0, orderCommand.getExitCode());
    }
}
