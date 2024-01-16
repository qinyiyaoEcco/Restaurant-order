package lab28.group4.asm1.commands.menu;

import lab28.group4.asm1.models.MenuItem;
import lab28.group4.asm1.repositories.MenuItemRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class MenuUpdateCommandTest {

    @Autowired
    private MenuUpdateCommand menuUpdateCommand;

    @Autowired
    private MenuItemRepository menuItemRepository;

    @BeforeEach
    void setUp() {
        MenuItem menuItem = new MenuItem("name", "description", 5);
        menuItemRepository.save(menuItem);
    }

    @Test
    void updateName() {
        String[] args = {"menu-update", "-u", "admin", "-p", "admin", "--id", "1", "--name", "s"};
        menuUpdateCommand.run(args);
        org.junit.jupiter.api.Assertions.assertEquals(0, menuUpdateCommand.getExitCode());
        MenuItem menuItem = menuItemRepository.findById(1L).orElse(null);
        Assertions.assertNotNull(menuItem);
        Assertions.assertEquals("s", menuItem.getName());
        Assertions.assertEquals("description", menuItem.getDescription());
        Assertions.assertEquals(5, menuItem.getPrice());
    }

    @Test
    void updatePrice() {
        String[] args = {"menu-update", "-u", "admin", "-p", "admin", "--id", "1", "--price", "6"};
        menuUpdateCommand.run(args);
        org.junit.jupiter.api.Assertions.assertEquals(0, menuUpdateCommand.getExitCode());
        MenuItem menuItem = menuItemRepository.findById(1L).orElse(null);
        Assertions.assertNotNull(menuItem);
        Assertions.assertEquals("name", menuItem.getName());
        Assertions.assertEquals("description", menuItem.getDescription());
        Assertions.assertEquals(6, menuItem.getPrice());
    }

    @Test
    void updateDescription() {
        String[] args = {"menu-update", "-u", "admin", "-p", "admin", "--id", "1", "--description", "s"};
        menuUpdateCommand.run(args);
        org.junit.jupiter.api.Assertions.assertEquals(0, menuUpdateCommand.getExitCode());
        MenuItem menuItem = menuItemRepository.findById(1L).orElse(null);
        Assertions.assertNotNull(menuItem);
        Assertions.assertEquals("name", menuItem.getName());
        Assertions.assertEquals("s", menuItem.getDescription());
        Assertions.assertEquals(5, menuItem.getPrice());
    }

    @Test
    void updateInvalid() {
        String[] args = {"menu-update", "-u", "admin", "-p", "admin", "--id", "2", "--name", "s"};
        menuUpdateCommand.run(args);
        org.junit.jupiter.api.Assertions.assertEquals(1, menuUpdateCommand.getExitCode());
    }

}
