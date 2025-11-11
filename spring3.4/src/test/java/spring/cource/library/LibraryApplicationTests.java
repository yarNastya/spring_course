package spring.cource.library;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.stereotype.Component;

//@SpringBootTest(properties = {"spring.shell.interactive.enabled=false", "spring.shell.script.spring.shell.script=false"})
@SpringBootTest
@ComponentScan
class LibraryApplicationTests {
	@Test
	void contextLoads() {
	}

}
