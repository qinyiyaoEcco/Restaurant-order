package lab28.group4.asm1.commands.cart;

import lab28.group4.asm1.Application;
import lab28.group4.asm1.models.CartItem;
import lab28.group4.asm1.models.MenuItem;
import lab28.group4.asm1.repositories.CartItemRepository;
import lab28.group4.asm1.repositories.MenuItemRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(classes = Application.class)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class CheckoutCommandTest {

    @Autowired
    private CheckoutCommand checkoutCommand;

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Test
    void checkoutNoDeliveryAndPickup() {
        String[] args = {"checkout"};
        checkoutCommand.run(args);
        Assertions.assertEquals(1, checkoutCommand.getExitCode());
    }

    @Test
    void checkoutDeliveryAndPickup() {
        String[] args = {"checkout", "--delivery", "--pickup"};
        checkoutCommand.run(args);
        Assertions.assertEquals(1, checkoutCommand.getExitCode());
    }

    @Test
    void checkoutEmpty() {
        String[] args = {"checkout", "--delivery"};
        checkoutCommand.run(args);
        Assertions.assertEquals(1, checkoutCommand.getExitCode());
    }

    @Test
    void checkout() {
        MenuItem menuItem = new MenuItem("s", "s", 5);
        menuItemRepository.save(menuItem);
        CartItem cartItem = new CartItem(menuItem);
        cartItemRepository.save(cartItem);
        String[] args = {"checkout", "--delivery"};
        checkoutCommand.run(args);
        Assertions.assertEquals(0, checkoutCommand.getExitCode());
    }
}
