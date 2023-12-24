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
								"/auth/login", "/api/users/register", "/api/activities/{parkId}", "/api/parks/{parkId}",
								"/api/reviews/park/{parkId}", "/api/parks")
						.permitAll() // Allowing access to specified endpoints without authentication
						.requestMatchers(HttpMethod.PUT, "/api/users/{userId}",
								"/api/reviews/park/{parkId}/user/{userId}/review/{reviewId}",
								"/api/addresses/users/{userId}/update-addresses",
								"/api/user/{userId}/tickets/{ticketId}")
						.hasAnyRole("USER", "ADMIN") // Accessible to users only
						.requestMatchers(HttpMethod.DELETE, "/api/users/{userId}",
								"/api/reviews/park/{parkId}/user/{userId}/{reviewId}",
								"/api/addresses/users/{userId}/delete-addresses/{addressId}",
								"/api/user/{userId}/tickets/{ticketId}")
						.hasAnyRole("USER", "ADMIN") // Accessible to users only
						.requestMatchers(HttpMethod.GET, "/api/users/currentUser", "/api/addresses/users/{userId}",
								"/api/user/{userId}/tickets", "/api/user/{userId}/tickets/{ticketId}")
						.hasAnyRole("USER", "ADMIN") // Accessible to users only
						.requestMatchers(HttpMethod.POST, "/api/reviews/park/{parkId}/user/{userId}",
								"/api/addresses/users/{userId}", "/api/addresses/bulk/users/{userId}",
								"/api/user/{userId}/tickets")
						.hasAnyRole("USER", "ADMIN") // Accessible to users only
						.requestMatchers(HttpMethod.GET, "/api/users/{userId}", "/api/users/all", "/api/parks/{parkId}")
						.hasRole("ADMIN") // Accessible to admins only via GET
						.requestMatchers(HttpMethod.PUT, "/api/parks/{parkId}", "/api/activities").hasRole("ADMIN")
						.requestMatchers(HttpMethod.DELETE, "/api/parks/{parkId}",
								"/api/activities/park/{parkId}/activity/{activityId}")
						.hasAnyRole("ADMIN") // Accessible to admins only via DELETE
						.requestMatchers(HttpMethod.POST, "/api/parks", "/api/activities/park/{parkId}",
								"/api/activities/bulk/park/{parkId}")
						.hasRole("ADMIN") // Accessible to admins only via POST
						.anyRequest().authenticated() // All other requests need authentication

				).exceptionHandling(ex -> ex.authenticationEntryPoint(point)) // Handling authentication exceptions with
																				// specified entry point
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		// Setting session creation policy to STATELESS

		http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
		// Adding JwtAuthenticationFilter before UsernamePasswordAuthenticationFilter in
		// the filter chain

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
