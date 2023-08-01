package com.test.graphql.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class BeanConfig {

    @Bean
    public Duration duration() {
        return Duration.ZERO;
    }
}
