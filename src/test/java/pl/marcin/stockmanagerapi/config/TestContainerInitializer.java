package pl.marcin.stockmanagerapi.config;

import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.GenericApplicationContext;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;


@Configuration
public class TestContainerInitializer implements ApplicationContextInitializer<GenericApplicationContext> {

    @Override
    public void initialize(GenericApplicationContext applicationContext) {
        new MySqlDatabaseInitializer().initialize(applicationContext);
    }

    static class MySqlDatabaseInitializer {
        private static final DockerImageName POSTGRES_IMAGE = DockerImageName.parse("postgis/postgis:15-3.3")
                .asCompatibleSubstituteFor("postgres");
        private static final PostgreSQLContainer mySQLContainer = new PostgreSQLContainer<>(POSTGRES_IMAGE)
                .withDatabaseName("testdb")
                .withUsername("sa")
                .withPassword("sa")
                .withInitScript("data-h2.sql")
                ;

        static {
            mySQLContainer.start();
        }

        public void initialize(GenericApplicationContext applicationContext) {
            TestPropertyValues.of(
                    "spring.datasource.jdbc-url=" + mySQLContainer.getJdbcUrl(),
                    "spring.datasource.url=" + mySQLContainer.getJdbcUrl(),
                    "spring.datasource.username=" + mySQLContainer.getUsername(),
                    "spring.datasource.password=" + mySQLContainer.getPassword()
            ).applyTo(applicationContext.getEnvironment());
        }
    }
}
