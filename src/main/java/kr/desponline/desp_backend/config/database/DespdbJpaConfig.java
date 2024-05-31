package kr.desponline.desp_backend.config.database;

import com.zaxxer.hikari.HikariDataSource;
import java.util.HashMap;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableJpaRepositories(basePackages = "kr.desponline.desp_backend.repository.mysql.despdb",
    entityManagerFactoryRef = "despdbEntityManagerFactory",
    transactionManagerRef = "despdbTransactionManager")
public class DespdbJpaConfig {

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.despdb")
    public DataSource despdbDataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Primary
    @Bean(name = "despdbEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean despdbEntityManagerFactory(
        @Qualifier("despdbDataSource") DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan("kr.desponline.desp_backend.entity.mysql.despdb"); // despdb 엔티티 패키지 위치

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setShowSql(true);
        em.setJpaVendorAdapter(vendorAdapter);

        HashMap<String, Object> prop = new HashMap<>();
        prop.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        prop.put("hibernate.hbm2ddl.auto", "none");
        prop.put("hibernate.format_sql", true);
        em.setJpaPropertyMap(prop);
        return em;
    }

    @Primary
    @Bean
    public PlatformTransactionManager despdbTransactionManager(
        @Qualifier("despdbEntityManagerFactory") LocalContainerEntityManagerFactoryBean despdbEntityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(despdbEntityManagerFactory.getObject());
        transactionManager.setTransactionSynchronization(JpaTransactionManager.SYNCHRONIZATION_ON_ACTUAL_TRANSACTION);
        return transactionManager;
    }
}
