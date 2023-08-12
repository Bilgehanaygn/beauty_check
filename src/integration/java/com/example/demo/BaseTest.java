package com.example.demo;


import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.TestPropertySourceUtils;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

import java.lang.annotation.Inherited;


public class BaseTest implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    private final static PostgreSQLContainer<?> container = new PostgreSQLContainer<>(
            DockerImageName.parse("postgres:latest")
                    .asCompatibleSubstituteFor("postgres"))
            .withUsername("postgres")
            .withPassword("q");

    @Override
    public void initialize(ConfigurableApplicationContext context){
        TestPropertySourceUtils.addInlinedPropertiesToEnvironment(
                context,
                "spring.datasource.url=".concat(container.getJdbcUrl())
        );
    }

    //An alternative way to context configuration
//    @DynamicPropertySource
//    public static void overrideProps(DynamicPropertyRegistry registry){
//        registry.add("spring.datasource.url", container::getJdbcUrl);
////        registry.add("spring.datasource.username", container::getUsername);
////        registry.add("spring.datasource.password", container::getPassword);
////        registry.add("spring.test.database.replace", ()->"none");
//    }

    static {
        container.start();
    }
}
