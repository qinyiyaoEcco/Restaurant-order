package lab28.group4.asm1.commands;

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
public class UserCommandTest {

    @Autowired
    private UserCommand userCommand;

    @Test
    void user() {
        String[] args = {"user", "-u", "admin", "-p", "admin"};
        userCommand.run(args);
        Assertions.assertEquals(0, userCommand.getExitCode());
    }

}
