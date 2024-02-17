package kr.desponline.desp_backend.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    // 빈 등록, 기본으로 지정
    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.despdb")
    public DataSource despdbDataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.webgamedb")
    public DataSource webgamedbDataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Primary
    @Bean(name = "despdbEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean despdbEntityManagerFactory(
        @Qualifier("despdbDataSource") DataSource dataSource,
        JpaProperties jpaProperties) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan("kr.desponline.desp_backend.entity.despdb"); // despdb 엔티티 패키지 위치
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        em.setJpaPropertyMap(jpaProperties.getProperties());
        return em;
    }

    // webgamedb에 대한 EntityManagerFactory 설정
    @Bean(name = "webgamedbEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean webgamedbEntityManagerFactory(
        @Qualifier("webgamedbDataSource") DataSource dataSource,
        JpaProperties jpaProperties) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan("kr.desponline.desp_backend.entity.webgamedb"); // webgamedb 엔티티 패키지 위치
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        em.setJpaPropertyMap(jpaProperties.getProperties());
        return em;
    }

    // 각 데이터 소스에 대한 TransactionManager 설정
    @Primary
    @Bean
    public JpaTransactionManager despdbTransactionManager(
        @Qualifier("despdbEntityManagerFactory") LocalContainerEntityManagerFactoryBean despdbEntityManagerFactory) {
        return new JpaTransactionManager(despdbEntityManagerFactory.getObject());
    }

    @Bean
    public JpaTransactionManager webgamedbTransactionManager(
        @Qualifier("webgamedbEntityManagerFactory") LocalContainerEntityManagerFactoryBean webgamedbEntityManagerFactory) {
        return new JpaTransactionManager(webgamedbEntityManagerFactory.getObject());
    }
}
