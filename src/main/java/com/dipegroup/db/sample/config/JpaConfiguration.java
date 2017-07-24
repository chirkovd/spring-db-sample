package com.dipegroup.db.sample.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Project: spring-db-sample
 * Description:
 * Date: 7/24/2017
 *
 * @author Dmitriy_Chirkov
 * @since 1.8
 */
@EnableJpaAuditing
@EnableJpaRepositories(basePackages = "com.dipegroup.db.sample.repositories")
@EnableTransactionManagement
@Configuration
public class JpaConfiguration {

    @Bean
    public AuditorAware<String> auditorAware() {
        return () -> "user";
    }
}
