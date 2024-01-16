package lab28.group4.asm1.commands.shell.session;

import lab28.group4.asm1.Application;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = Application.class)
@AutoConfigureTestDatabase
public class LogoutCommandTest {

    @Autowired
    private LogoutCommand logoutCommand;

    @Test
    void logout() {
        Session.setSession("admin", "admin");
        String[] args = {"logout"};
        logoutCommand.run(args);
        Assertions.assertEquals(0, logoutCommand.getExitCode());
        Assertions.assertNull(Session.getUsername());
        Assertions.assertNull(Session.getPassword());
    }

}
