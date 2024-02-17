package kr.desponline.desp_backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "kr.desponline.desp_backend.mysql_repository.webgamedb",
    entityManagerFactoryRef = "webgamedbEntityManagerFactory",
    transactionManagerRef = "webgamedbTransactionManager")
public class WebgamedbJpaConfig {
}
