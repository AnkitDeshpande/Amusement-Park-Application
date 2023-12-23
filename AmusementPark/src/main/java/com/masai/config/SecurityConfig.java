package com.masai.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Security Configuration class responsible for configuring Spring Security settings.
 */
@Configuration
public class SecurityConfig {

    @Autowired
    private JWTAuthenticationEntryPoint point;

    @Autowired
    private JwtAuthenticationFilter filter;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Configures the SecurityFilterChain for handling HTTP security configurations.
     *
     * @param http HttpSecurity instance to configure security
     * @return SecurityFilterChain with specified security configurations
     * @throws Exception if there's an issue while configuring security
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable) // Disabling CSRF protection
                .cors(AbstractHttpConfigurer::disable) // Disabling Cross-Origin Resource Sharing
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/login", "/api/users/register", "/api/activities/{parkId}", "/api/parks/{parkId}", "/api/reviews/park/{parkId}")
                        .permitAll() // Allowing access to specified endpoints without authentication
                        .anyRequest().authenticated() // Requiring authentication for any other requests
                )
                .exceptionHandling(ex -> ex.authenticationEntryPoint(point)) // Handling authentication exceptions with specified entry point
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)); // Setting session creation policy to STATELESS

        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class); // Adding JwtAuthenticationFilter before UsernamePasswordAuthenticationFilter in the filter chain
        return http.build(); // Building and returning the SecurityFilterChain
    }

    @Bean
    public DaoAuthenticationProvider doDaoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();

        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);

        return daoAuthenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration builder) throws Exception {
        return builder.getAuthenticationManager();
    }
}
