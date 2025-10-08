package com.insurence.configuration;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

	@Autowired
	private JwtAuthFilter jwtAuthFilter;

//	@Bean
//	public SecurityFilterChain securityFilterChain(HttpSecurity https) throws Exception {
//		https.csrf().disable().cors()
//		.and()
//		.authorizeHttpRequests(auth -> auth
//	            .requestMatchers("/user/register", "/user/login").permitAll()
//	            .anyRequest().authenticated()
//	        )
//		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//
//		https.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
//
//		return https.build();
//	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http.csrf(csrf -> csrf.disable())
				.authorizeHttpRequests(auth -> auth
						.requestMatchers("/user/register", "/user/login").permitAll()
						.anyRequest().authenticated()
				)

				// 3. Set session management to stateless
				// Using the modern lambda style for clarity
				.sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

				// 4. CORRECTED: Add the custom JWT filter BEFORE the standard filter
				// This line is now part of the chain, using the correct 'http' object
				.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)

				// 5. Build and return the SecurityFilterChain
				.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
	    return config.getAuthenticationManager();
	}
	
	 @Bean
	 public CorsConfigurationSource corsConfigurationSource() {
	        CorsConfiguration corsConfig = new CorsConfiguration();
	        corsConfig.setAllowedOrigins(List.of("http://localhost:5173")); 
	        corsConfig.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
	        corsConfig.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
	        corsConfig.setAllowCredentials(true);

	        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	        source.registerCorsConfiguration("/**", corsConfig);
	        return source;
	 }

}
