package dev.akanksha.elipides.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class Encoder {
    @Bean
    public BCryptPasswordEncoder createPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
