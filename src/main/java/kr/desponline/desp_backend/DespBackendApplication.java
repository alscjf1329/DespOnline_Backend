package kr.desponline.desp_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@SpringBootApplication
@EnableReactiveMongoRepositories
public class DespBackendApplication {
    public static void main(String[] args) {
        SpringApplication.run(DespBackendApplication.class, args);
    }
}