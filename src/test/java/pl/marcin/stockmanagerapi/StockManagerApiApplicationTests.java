package pl.marcin.stockmanagerapi;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import pl.marcin.stockmanagerapi.config.TestContainerInitializer;

@AutoConfigureMockMvc
@SpringBootTest
@ContextConfiguration(classes = {StockManagerApiApplication.class}, initializers = {TestContainerInitializer.class})
class StockManagerApiApplicationTests {

	@Test
	void contextLoads() {
	}

}
