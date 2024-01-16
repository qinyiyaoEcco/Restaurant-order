package lab28.group4.asm1.commands.admin;

import lab28.group4.asm1.Application;
import lab28.group4.asm1.Role;
import lab28.group4.asm1.models.User;
import lab28.group4.asm1.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(classes = Application.class)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class AdminCommandTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AdminCommand adminCommand;

    @Test
    void isAdmin() {
        String[] args = {"admin", "-u", "admin", "-p", "admin"};
        adminCommand.run(args);
        Assertions.assertEquals(0, adminCommand.getExitCode());
    }

    @Test
    void isNotAdmin() {
        User user = new User("user", "user", Role.USER);
        userRepository.save(user);
        String[] args = {"admin", "-u", "user", "-p", "user"};
        adminCommand.run(args);
        Assertions.assertNotEquals(0, adminCommand.getExitCode());
    }

}
