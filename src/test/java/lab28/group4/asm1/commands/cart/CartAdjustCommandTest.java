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
public class CartAdjustCommandTest {

    @Autowired
    private CartAdjustCommand cartAdjustCommand;

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Test
    void adjustNonExist() {
        String[] args = {"cart-adjust", "--id", "1", "--quantity", "1"};
        cartAdjustCommand.run(args);
        Assertions.assertEquals(1, cartAdjustCommand.getExitCode());
    }

    @Test
    void adjust() {
        MenuItem menuItem = new MenuItem("s", "s", 5);
        menuItemRepository.save(menuItem);
        cartItemRepository.save(new CartItem(menuItem));
        String[] args = {"cart-adjust", "--id", "1", "--quantity", "1"};
        cartAdjustCommand.run(args);
        Assertions.assertEquals(0, cartAdjustCommand.getExitCode());
        CartItem cartItem = cartItemRepository.findCartItemByMenuItem(menuItem).orElse(null);
        Assertions.assertNotNull(cartItem);
        Assertions.assertEquals(1, cartItem.getQuantity());
    }

}
