package lab28.group4.asm1.commands.shell.session;

import lab28.group4.asm1.Application;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = Application.class)
@AutoConfigureTestDatabase
public class LoginCommandTest {

    @Autowired
    private LoginCommand loginCommand;

    @Test
    void login() {
        String[] args = {"login", "-u", "admin", "-p", "admin"};
        loginCommand.run(args);
        Assertions.assertEquals(0, loginCommand.getExitCode());
        Assertions.assertNotNull(Session.getUsername());
        Assertions.assertNotNull(Session.getPassword());
    }

}
