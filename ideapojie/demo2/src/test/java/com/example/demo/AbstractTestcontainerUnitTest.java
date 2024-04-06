package com.example.demo;

import com.github.javafaker.Faker;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.sql.DataSource;

@Testcontainers
public abstract class  AbstractTestcontainerUnitTest {

    @BeforeAll
    static void beforeAll() {
        Flyway flyway = Flyway.configure().dataSource(
                postgreSQLContainer.getJdbcUrl(),
                postgreSQLContainer.getUsername(),
                postgreSQLContainer.getPassword()
        ).load();
        flyway.migrate();
    }

    @Container
    protected static final PostgreSQLContainer<?> postgreSQLContainer =
            new PostgreSQLContainer<>("postgres:latest")
                    .withDatabaseName("postgres-dao-unit-test")
                    .withUsername("postgres")
                    .withPassword("password");

    @DynamicPropertySource
    private static void registerDataSourcePropertied(
            DynamicPropertyRegistry registry) {
        registry.add(
                "spring.datasource.url",
                postgreSQLContainer::getJdbcUrl
        );
        registry.add(
                "spring.datasource.username",
                postgreSQLContainer::getUsername
        );
        registry.add(
                "spring.datasource.password",
                postgreSQLContainer::getPassword
        );
    }
    private static DataSource getDataSource(){
        DataSourceBuilder builder =DataSourceBuilder.create()
                .driverClassName(postgreSQLContainer.getDriverClassName())
                .url(postgreSQLContainer.getJdbcUrl())
                .username(postgreSQLContainer.getUsername())
                .password(postgreSQLContainer.getPassword());
        return builder.build();
    }

    protected static JdbcTemplate getJdbcTemplate(){
        return new JdbcTemplate(getDataSource());
    }

    /*假数据添加*/
    protected static final Faker FAKER = new Faker();
    //    @Test
//    void canStartPostgresDB() {
//        assertThat(postgreSQLContainer.isRunning()).isTrue();
//        assertThat(postgreSQLContainer.isCreated()).isTrue();
//    }
//
//    @Test
//    void canApplyDbMigrationsWithFlyway() {
//        Flyway flyway = Flyway.configure().dataSource(
//                postgreSQLContainer.getJdbcUrl(),
//                postgreSQLContainer.getUsername(),
//                postgreSQLContainer.getPassword()
//        ).load();
//        flyway.migrate();
//
//    }
}
