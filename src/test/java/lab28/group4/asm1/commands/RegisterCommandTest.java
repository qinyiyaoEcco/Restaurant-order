package lab28.group4.asm1.commands;

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
public class RegisterCommandTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RegisterCommand registerCommand;

    @Test
    void registerAdmin() {
        String[] args = {"register", "-u", "admin", "-p", "admin"};
        registerCommand.run(args);
        Assertions.assertNotEquals(0, registerCommand.getExitCode());
    }

    @Test
    void registerUser() {
        String[] args = {"register", "-u", "user", "-p", "user"};
        registerCommand.run(args);
        Assertions.assertEquals(0, registerCommand.getExitCode());
        User user = userRepository.findByUsername("user");
        Assertions.assertNotNull(user);
        Assertions.assertEquals(Role.USER, user.getRole());
    }

}
