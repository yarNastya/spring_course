package spring.cource.library;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {"spring.shell.interactive.enabled=false", "spring.shell.script.spring.shell.script=false"})
class LibraryApplicationTests {

	@Test
	void contextLoads() {
	}

}
