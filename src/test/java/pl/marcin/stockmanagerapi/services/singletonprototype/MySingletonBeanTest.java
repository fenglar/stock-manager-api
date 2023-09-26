package pl.marcin.stockmanagerapi.services.singletonprototype;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import pl.marcin.stockmanagerapi.StockManagerApiApplication;
import pl.marcin.stockmanagerapi.config.TestContainerInitializer;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = {StockManagerApiApplication.class},
        initializers = {TestContainerInitializer.class})
@AutoConfigureMockMvc
@ActiveProfiles("integration-test")
class MySingletonBeanTest {

    @Autowired
    private MySingletonBean mySingletonBean;

    @Test
    void executeFromDirectInjection() {
        log.info("########################################################");
        log.info("########## executeFromDirectInjection     ##############");
        log.info("########################################################");
        mySingletonBean.executeFromDirectInjection();
        mySingletonBean.executeFromDirectInjection();
        mySingletonBean.executeFromDirectInjection();
        mySingletonBean.executeFromDirectInjection();
        mySingletonBean.executeFromDirectInjection();
        mySingletonBean.executeFromDirectInjection();
        mySingletonBean.executeFromDirectInjection();
        mySingletonBean.executeFromDirectInjection();
        mySingletonBean.executeFromDirectInjection();
        log.info("########################################################");
    }

    @Test
    void executeFromObjectProvider() {
        log.info("########################################################");
        log.info("########## executeFromObjectProvider      ##############");
        log.info("########################################################");
        mySingletonBean.executeFromObjectProvider();
        mySingletonBean.executeFromObjectProvider();
        mySingletonBean.executeFromObjectProvider();
        mySingletonBean.executeFromObjectProvider();
        mySingletonBean.executeFromObjectProvider();
        mySingletonBean.executeFromObjectProvider();
        mySingletonBean.executeFromObjectProvider();
        mySingletonBean.executeFromObjectProvider();
        mySingletonBean.executeFromObjectProvider();
        log.info("########################################################");
    }

    @Test
    void executeFromApplicationContext() {
        log.info("########################################################");
        log.info("########## executeFromApplicationContext  ##############");
        log.info("########################################################");
        mySingletonBean.executeFromApplicationContext();
        mySingletonBean.executeFromApplicationContext();
        mySingletonBean.executeFromApplicationContext();
        mySingletonBean.executeFromApplicationContext();
        mySingletonBean.executeFromApplicationContext();
        mySingletonBean.executeFromApplicationContext();
        mySingletonBean.executeFromApplicationContext();
        mySingletonBean.executeFromApplicationContext();
        mySingletonBean.executeFromApplicationContext();
        log.info("########################################################");
    }
}