package com.masai.config;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * JwtAuthenticationFilter extends OncePerRequestFilter Custom filter
 * responsible for authenticating JWT tokens.
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private Logger logger = LoggerFactory.getLogger(OncePerRequestFilter.class);

	@Autowired
	private JwtHelper jwtHelper;

	@Autowired
	private UserDetailsService userDetailsService;

	/**
	 * Same contract as for {@code doFilter}, but guaranteed to be just invoked once
	 * per request within a single request thread. See
	 * {@link #shouldNotFilterAsyncDispatch()} for details.
	 * <p>
	 * Provides HttpServletRequest and HttpServletResponse arguments instead of the
	 * default ServletRequest and ServletResponse ones.
	 *
	 * @param request     HTTP Servlet request
	 * @param response    HTTP Servlet response
	 * @param filterChain FilterChain for managing the filter execution
	 * @throws ServletException if there's an issue with the servlet
	 * @throws IOException      if there's an I/O issue
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		// Extracting token from Authorization header
		String requestHeader = request.getHeader("Authorization");
		logger.info("Header: {}", requestHeader);
		String username = null;
		String token = null;

		// Checking if Authorization header has a Bearer token
		if (requestHeader != null && requestHeader.startsWith("Bearer")) {
			token = requestHeader.substring(7);
			try {
				// Retrieving username from the JWT token
				username = this.jwtHelper.getUsernameFromToken(token);
			} catch (IllegalArgumentException e) {
				logger.info("Illegal Argument while fetching the username !!");
				e.printStackTrace();
			} catch (ExpiredJwtException e) {
				logger.info("Given JWT token is expired !!");
				e.printStackTrace();
			} catch (MalformedJwtException e) {
				logger.info("Changes have been made to the token !! Invalid Token");
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			logger.info("Invalid Header Value !! ");
		}

		// Authenticating user if username is retrieved and user is not already
		// authenticated
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
			Boolean validateToken = this.jwtHelper.validateToken(token, userDetails);

			if (validateToken) {
				// Setting the authentication details
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authentication);
			} else {
				logger.info("Validation fails !!");
			}
		}

		// Proceeding to the next filter in the chain
		filterChain.doFilter(request, response);
	}
}
