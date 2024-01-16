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
public class MenuRemoveCommandTest {

    @Autowired
    private MenuRemoveCommand menuRemoveCommand;

    @Autowired
    private MenuItemRepository menuItemRepository;

    @BeforeEach
    void setUp() {
        MenuItem menuItem = new MenuItem("name", "description", 5);
        menuItemRepository.save(menuItem);
    }

    @Test
    void remove() {
        String[] args = {"menu-remove", "-u", "admin", "-p", "admin", "--id", "1"};
        menuRemoveCommand.run(args);
        Assertions.assertEquals(0, menuRemoveCommand.getExitCode());
    }

    @Test
    void removeInvalid() {
        String[] args = {"menu-remove", "-u", "admin", "-p", "admin", "--id", "2"};
        menuRemoveCommand.run(args);
        Assertions.assertEquals(1, menuRemoveCommand.getExitCode());
    }

}
