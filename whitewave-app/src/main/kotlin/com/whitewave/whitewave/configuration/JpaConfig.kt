package com.whitewave.whitewave.configuration

import jakarta.persistence.EntityManagerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.annotation.EnableTransactionManagement
import javax.sql.DataSource

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "jpaEntityManagerFactory", transactionManagerRef = "jpaTransactionManager", basePackages = ["com.whitewave.whitewave.repository"])
//@PropertySource(value = ["classpath:application.properties"])
class JpaConfig {
    @Bean(name = ["jpaEntityManagerFactory"])
    fun jpaEntityManagerFactory(builder: EntityManagerFactoryBuilder,
                                @Autowired dataSource: DataSource?): LocalContainerEntityManagerFactoryBean {
        return builder.dataSource(dataSource).packages("com.whitewave.whitewave.model").build()
    }

    @Bean(name = ["jpaTransactionManager"])
    fun transactionManager(@Qualifier("jpaEntityManagerFactory") entityManagerFactory: EntityManagerFactory?): PlatformTransactionManager {
        val transactionManager = JpaTransactionManager()
        transactionManager.entityManagerFactory = entityManagerFactory
        return transactionManager
    }
}
