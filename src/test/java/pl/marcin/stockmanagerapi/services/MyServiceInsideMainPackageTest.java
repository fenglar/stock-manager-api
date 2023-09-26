package pl.marcin.stockmanagerapi.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;
import pl.marcin.stockmanagerapi.StockManagerApiApplication;
import pl.marcin.stockmanagerapi.config.TestContainerInitializer;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = {StockManagerApiApplication.class},
        initializers = {TestContainerInitializer.class})
@AutoConfigureMockMvc
@ActiveProfiles("integration-test")
class MyServiceInsideMainPackageTest {

    @Autowired
    private MyServiceInsideMainPackage myServiceInsideMainPackage;

    @Test
    void executeTest() {
        myServiceInsideMainPackage.execute();
    }

}