package com.masai.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AppConfig {

    /*
     * @Bean
     * public UserDetailsService userDetailsService() {
     *    UserDetails user = User.builder().username("ankit").password(passwordEncoder().encode("abc")).roles("ADMIN").build();
     *    return new InMemoryUserDetailsManager(user);
     * }
     */

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
