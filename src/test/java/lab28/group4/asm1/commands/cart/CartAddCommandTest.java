package lab28.group4.asm1.commands.cart;

import lab28.group4.asm1.Application;
import lab28.group4.asm1.models.MenuItem;
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
public class CartAddCommandTest {

    @Autowired
    private CartAddCommand cartAddCommand;

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Test
    void addNonExist() {
        String[] args = {"cart-add", "--id", "1", "--quantity", "1"};
        cartAddCommand.run(args);
        Assertions.assertEquals(1, cartAddCommand.getExitCode());
    }

    @Test
    void add() {
        menuItemRepository.save(new MenuItem("s", "s", 5));
        String[] args = {"cart-add", "--id", "1", "--quantity", "1"};
        cartAddCommand.run(args);
        Assertions.assertEquals(0, cartAddCommand.getExitCode());
    }

}
