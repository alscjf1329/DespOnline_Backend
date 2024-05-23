package kr.desponline.desp_backend.config;

import com.zaxxer.hikari.HikariDataSource;
import java.util.HashMap;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

@Configuration
@EnableJpaRepositories(basePackages = "kr.desponline.desp_backend.mysql_repository.webgamedb",
    entityManagerFactoryRef = "webgamedbEntityManagerFactory",
    transactionManagerRef = "webgamedbTransactionManager")
public class WebgamedbJpaConfig {

    @Bean
    @ConfigurationProperties(prefix = "spring.webgamedb")
    public DataSource webgamedbDataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }


    // webgamedb에 대한 EntityManagerFactory 설정
    @Bean(name = "webgamedbEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean webgamedbEntityManagerFactory(
        @Qualifier("webgamedbDataSource") DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan("kr.desponline.desp_backend.entity.mysql.webgamedb"); // webgamedb 엔티티 패키지 위치

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setShowSql(true);
        em.setJpaVendorAdapter(vendorAdapter);

        HashMap<String, Object> prop = new HashMap<>();
        prop.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        prop.put("hibernate.hbm2ddl.auto", "update");
        prop.put("hibernate.format_sql", true);
        em.setJpaPropertyMap(prop);
        return em;
    }

    @Bean
    public JpaTransactionManager webgamedbTransactionManager(
        @Qualifier("webgamedbEntityManagerFactory") LocalContainerEntityManagerFactoryBean webgamedbEntityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(webgamedbEntityManagerFactory.getObject());
        return transactionManager;
    }
}
