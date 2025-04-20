package com.fiap.tech.challenge.datasource;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

import static java.util.Collections.singletonMap;

@Configuration
@EnableJpaRepositories(
        entityManagerFactoryRef = "localEntityManagerFactory",
        transactionManagerRef = "localTransactionManager",
        basePackages = "com.fiap.tech.challenge.domain"
)
@EnableTransactionManagement
public class LocalDataSourceConfig {

    private final Environment environment;

    @Autowired
    public LocalDataSourceConfig(Environment environment) {
        this.environment = environment;
    }

    @Primary
    @Bean(name = "localEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean localEntityManagerFactory(final EntityManagerFactoryBuilder builder, final @Qualifier("local-db") DataSource dataSource) {
        return builder
                .dataSource(dataSource)
                .packages("com.fiap.tech.challenge.domain")
                .persistenceUnit("localDb")
                .properties(singletonMap("hibernate.hbm2ddl.auto", environment.getProperty("spring.jpa.hibernate.ddl-auto")))
                .build();
    }

    @Primary
    @Bean(name = "localTransactionManager")
    public PlatformTransactionManager localTransactionManager(final @Qualifier("localEntityManagerFactory") EntityManagerFactory localEntityManagerFactory) {
        return new JpaTransactionManager(localEntityManagerFactory);
    }
}