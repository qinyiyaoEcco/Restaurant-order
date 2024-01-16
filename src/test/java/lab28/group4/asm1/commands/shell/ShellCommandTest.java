package lab28.group4.asm1.commands.shell;

import lab28.group4.asm1.Application;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

@SpringBootTest(classes = Application.class)
@AutoConfigureTestDatabase
public class ShellCommandTest {

    private final InputStream originalIn = System.in;

    @Autowired
    private ShellCommand shellCommand;

    @Test
    void command() {
        String[] inputs = {"help", "login -u admin -p admin", "admin", "exit"};
        InputStream in = new ByteArrayInputStream(String.join(System.getProperty("line.separator"), inputs).getBytes());
        System.setIn(in);
        String[] args = {"shell", "--non-interactive"};
        shellCommand.run(args);
        Assertions.assertEquals(0, shellCommand.getExitCode());
    }

    @AfterEach
    void restoreSystemIn() {
        System.setIn(originalIn);
    }

}
