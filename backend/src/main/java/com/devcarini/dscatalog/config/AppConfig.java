package com.devcarini.dscatalog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password4j.BcryptPassword4jPasswordEncoder;

@Configuration
public class AppConfig {

    @Bean
    public BcryptPassword4jPasswordEncoder passwordEncoder(){
        return new BcryptPassword4jPasswordEncoder();
    }

}
