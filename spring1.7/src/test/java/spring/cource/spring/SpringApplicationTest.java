package spring.cource.spring;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {"spring.shell.interactive.enabled=false", "spring.shell.script.spring.shell.script=false"})

public class SpringApplicationTest {
    @Test
    void contextLoads() {
    }
}
