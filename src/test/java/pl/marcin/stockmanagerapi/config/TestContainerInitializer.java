package pl.marcin.stockmanagerapi.config;

import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.GenericApplicationContext;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.utility.DockerImageName;

@Configuration
public class TestContainerInitializer implements ApplicationContextInitializer<GenericApplicationContext> {

    @Override
    public void initialize(GenericApplicationContext applicationContext) {
        new MySqlDatabaseInitializer(applicationContext);
    }

    static class MySqlDatabaseInitializer {
        private final DockerImageName mySqlImage;
        private final MySQLContainer mySQLContainer;

        public MySqlDatabaseInitializer(GenericApplicationContext applicationContext) {
            mySqlImage = DockerImageName.parse("mysql:8.0.32")
                    .asCompatibleSubstituteFor("testdb");

            mySQLContainer = (MySQLContainer) new MySQLContainer(mySqlImage)
                    .withDatabaseName("testdb")
                    .withUsername("sa")
                    .withPassword("")
                    .withInitScript("scripts/data-h2.sql");

            mySQLContainer.start();

            TestPropertyValues.of(
                    "spring.datasource.jdbc-url=" + mySQLContainer.getJdbcUrl(),
                    "spring.datasource.url=" + mySQLContainer.getJdbcUrl(),
                    "spring.datasource.username=" + mySQLContainer.getUsername(),
                    "spring.datasource.password=" + mySQLContainer.getPassword()
            ).applyTo(applicationContext.getEnvironment());
        }
    }
}
