package kr.desponline.desp_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@EnableReactiveMongoRepositories
public class DespBackendApplication {
    public static void main(String[] args) {
        SpringApplication.run(DespBackendApplication.class, args);
    }
}