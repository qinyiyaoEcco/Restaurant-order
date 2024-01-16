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
public class AdminDemoteCommandTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AdminDemoteCommand adminDemoteCommand;

    @Test
    void demoteLastAdmin() {
        String[] args = {"admin-demote", "--target", "admin", "-u", "admin", "-p", "admin"};
        adminDemoteCommand.run(args);
        Assertions.assertEquals(1, adminDemoteCommand.getExitCode());
    }

    @Test
    void demoteNonExist() {
        String[] args = {"admin-demote", "--target", "user", "-u", "admin", "-p", "admin"};
        adminDemoteCommand.run(args);
        Assertions.assertEquals(1, adminDemoteCommand.getExitCode());
    }

    @Test
    void demoteAdmin() {
        User user = new User("user", "user", Role.ADMIN);
        userRepository.save(user);
        String[] args = {"admin-demote", "--target", "user", "-u", "admin", "-p", "admin"};
        adminDemoteCommand.run(args);
        Assertions.assertEquals(0, adminDemoteCommand.getExitCode());
        user = userRepository.findByUsername("user");
        Assertions.assertEquals(Role.USER, user.getRole());
    }

}
