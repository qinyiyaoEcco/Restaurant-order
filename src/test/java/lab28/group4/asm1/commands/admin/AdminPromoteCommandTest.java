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
public class AdminPromoteCommandTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AdminPromoteCommand adminPromoteCommand;

    @Test
    void promoteNonExist() {
        String[] args = {"admin-promote", "--target", "user", "-u", "admin", "-p", "admin"};
        adminPromoteCommand.run(args);
        Assertions.assertEquals(1, adminPromoteCommand.getExitCode());
    }

    @Test
    void promoteUser() {
        User user = new User("user", "user", Role.USER);
        userRepository.save(user);
        String[] args = {"admin-promote", "--target", "user", "-u", "admin", "-p", "admin"};
        adminPromoteCommand.run(args);
        Assertions.assertEquals(0, adminPromoteCommand.getExitCode());
        user = userRepository.findByUsername("user");
        Assertions.assertEquals(Role.ADMIN, user.getRole());
    }

}
