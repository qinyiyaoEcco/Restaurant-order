package lab28.group4.asm1.commands.menu;

import lab28.group4.asm1.models.MenuItem;
import lab28.group4.asm1.repositories.MenuItemRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class MenuCommandTest {

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Autowired
    private MenuCommand menuCommand;

    @Test
    void menu() {
        String[] args = {"menu"};
        menuCommand.run(args);
        Assertions.assertEquals(1, menuCommand.getExitCode());
        MenuItem menuItem = new MenuItem("name", "description", 5);
        menuItemRepository.save(menuItem);
        menuCommand.run(args);
        Assertions.assertEquals(0, menuCommand.getExitCode());
    }

}
