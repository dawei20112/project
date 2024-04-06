package com.example.demo;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class TextcontainersTest extends AbstractTestcontainerUnitTest {


    @Test
    void canStartPostgresDB() {
        assertThat(postgreSQLContainer.isRunning()).isTrue();
        assertThat(postgreSQLContainer.isCreated()).isTrue();
    }

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
