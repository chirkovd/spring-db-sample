package com.dipegroup.db.sample;

import com.dipegroup.exceptions.services.exceptions.ExceptionMapper;
import com.dipegroup.exceptions.services.messages.MessagesService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;

/**
 * Project: spring-db-sample
 * Description:
 * Date: 7/24/2017
 *
 * @author Dmitriy_Chirkov
 * @since 1.8
 */
@SpringBootApplication
public class DbApplication {

    @Bean
    public MessagesService messagesService() {
        return new MessagesService();
    }

    @Bean
    public ExceptionMapper exceptionMapper() {
        return new ExceptionMapper("spring-db-sample", messagesService());
    }

    @Bean
    public AuditorAware<String> auditorAware() {
        return () -> null;
    }

    public static void main(String[] args) {
        SpringApplication.run(DbApplication.class, args);
    }
}
