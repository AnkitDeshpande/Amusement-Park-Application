package com.masai.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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
 * Security Configuration class responsible for configuring Spring Security
 * settings.
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
						// .anyRequest().permitAll()
						.requestMatchers("/swagger-ui/**", "/v3/**", "/swagger-resources/**", "/webjars/**",
								"/auth/login", "/api/users/register", "/api/activities/**", "/api/reviews/park/**",
								"/api/parks")
						.permitAll() // Allowing access to specified endpoints without authentication
						.requestMatchers(HttpMethod.PUT, "/api/users/**", "/api/reviews/park/**",
								"/api/addresses/users/**")
						.hasAnyAuthority("USER") // Accessible to users only
						.requestMatchers(HttpMethod.DELETE, "/api/reviews/park/**", "/api/addresses/users/**",
								"/api/user/**")
						.hasAnyAuthority("USER") // Accessible to users only
						.requestMatchers(HttpMethod.GET, "/api/users/currentUser", "/api/addresses/users/**",
								"/api/user/**", "/api/reviews/**")
						.hasAnyAuthority("USER") // Accessible to users only
						.requestMatchers(HttpMethod.POST, "/api/reviews/park/**", "/api/addresses/users/**",
								"/api/addresses/bulk/users/**", "/api/user/**")
						.hasAnyAuthority("USER") // Accessible to users only
						.requestMatchers(HttpMethod.PUT, "/api/parks/**", "/api/activities").hasAuthority("ADMIN")
						.requestMatchers(HttpMethod.DELETE, "/api/parks/**", "/api/activities/park/**")
						.hasAuthority("ADMIN") // Accessible to admins only via DELETE
						.requestMatchers(HttpMethod.POST, "/api/parks/**", "/api/activities/park/**",
								"/api/activities/bulk/park/**")
						.hasAuthority("ADMIN") // Accessible to admins only via POST
						.anyRequest().authenticated() // All other requests need authentication

				).exceptionHandling(ex -> ex.authenticationEntryPoint(point)) // Handling authentication exceptions with
																				// specified entry point
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authenticationProvider(doDaoAuthenticationProvider())
				.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);

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
