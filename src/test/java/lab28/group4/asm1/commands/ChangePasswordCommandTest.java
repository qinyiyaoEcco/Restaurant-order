package lab28.group4.asm1.commands;

import lab28.group4.asm1.Application;
import lab28.group4.asm1.commands.shell.session.Session;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(classes = Application.class)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ChangePasswordCommandTest {

    @Autowired
    private UserCommand userCommand;

    @Autowired
    private ChangePasswordCommand changePasswordCommand;

    @Test
    void changePassword() {
        String[] args = {"user", "-u", "admin", "-p", "admin"};
        userCommand.run(args);
        Assertions.assertEquals(0, userCommand.getExitCode());
        args = new String[]{"change-password", "-u", "admin", "-p", "admin", "-n", "admin2"};
        changePasswordCommand.run(args);
        Assertions.assertEquals(0, changePasswordCommand.getExitCode());
        args = new String[]{"user", "-u", "admin", "-p", "admin"};
        userCommand.run(args);
        Assertions.assertNotEquals(0, userCommand.getExitCode());
        args = new String[]{"user", "-u", "admin", "-p", "admin2"};
        userCommand.run(args);
        Assertions.assertEquals(0, userCommand.getExitCode());
    }

    @Test
    void changePasswordSession() {
        Session.setSession("admin", "admin");
        String[] args = {"change-password", "-u", "admin", "-p", "admin", "-n", "admin2"};
        changePasswordCommand.run(args);
        Assertions.assertEquals(0, changePasswordCommand.getExitCode());
    }

}
