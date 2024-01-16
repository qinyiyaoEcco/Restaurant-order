package lab28.group4.asm1.commands.menu;

import lab28.group4.asm1.Application;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(classes = Application.class)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class MenuAddCommandTest {

    @Autowired
    private MenuAddCommand menuAddCommand;

    @Test
    void menuAdd() {
        String[] args = {"menu-add", "-u", "admin", "-p", "admin", "--name", "s", "--description", "s", "--price", "5"};
        menuAddCommand.run(args);
        Assertions.assertEquals(0, menuAddCommand.getExitCode());
    }

}
