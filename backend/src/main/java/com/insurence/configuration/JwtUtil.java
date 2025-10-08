package com.insurence.configuration;

import java.security.Key;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
	
	private final String SECRET = "mysecretkeymysecretkeymysecretkey";
	 private final Key key = Keys.hmacShaKeyFor(SECRET.getBytes());
	
	
	
	public String generateToken(String email, List<String> role) {
		return Jwts.builder()
				.subject(email)
				.claim("roles", role)
				.signWith(key, SignatureAlgorithm.HS256)
				.issuedAt(new Date())
				.expiration(new Date(System.currentTimeMillis() + (1000 * 60 * 60)))
				.compact();
				
	}
	
	
	public Claims extractClaims(String token) {
		return Jwts.parser()
				.setSigningKey(key)
				.build()
				.parseClaimsJws(token).getBody();
	}
	
	public String extractUser(String token) {
		return extractClaims(token).getSubject();
	}
	
	public List<String> extractRole(String token) {
	    return extractClaims(token).get("roles", List.class);  // ✅ match key
	}

	
	public boolean isValid(String token) {
		return !extractClaims(token).getExpiration().before(new Date());
	}
}
