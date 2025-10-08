package com.insurence.configuration;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

	@Autowired
	private JwtUtil jwtUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String path = request.getServletPath();

        if (path.equals("/user/register") || path.equals("/user/login")) {
            filterChain.doFilter(request, response);
            return;
        }


		String authHeader = request.getHeader("Authorization");
		String jwtToken;
		String email;
		

		if (authHeader != null && authHeader.startsWith("Bearer ")) {

			jwtToken = authHeader.substring(7);

			if (jwtUtil.isValid(jwtToken)) {
				email = jwtUtil.extractUser(jwtToken);
				List<String> roles = jwtUtil.extractRole(jwtToken);
				
				/*
				 * List<SimpleGrantedAuthority> authorities = roles.stream() .map(role -> new
				 * SimpleGrantedAuthority("ROLE_" + role)) .toList();
				 */
				
				List<SimpleGrantedAuthority> authorities = roles.stream()
					    .map(role -> role.startsWith("ROLE_") ? role : "ROLE_" + role)
					    .map(SimpleGrantedAuthority::new)
					    .toList();

				UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(email, null, authorities);

				auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

				SecurityContextHolder.getContext().setAuthentication(auth);

			}

		}

		filterChain.doFilter(request, response);

	}

}
