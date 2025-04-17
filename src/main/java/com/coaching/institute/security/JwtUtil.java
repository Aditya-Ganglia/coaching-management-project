package com.coaching.institute.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    private static final String SECRET_KEY = "this_is_a_very_secret_key_for_coaching_app_jwt_1234567890!";

    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 5; // 5 hours

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    // ✅ Generate token
    public String generateToken(String email, String role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // ✅ Extract email (subject)
    public String extractEmail(String token) {
        return parseToken(token).getBody().getSubject();
    }

    // ✅ Extract role from claims
    public String extractRole(String token) {
        return parseToken(token).getBody().get("role", String.class);
    }

    // ✅ Validate token
    public boolean isTokenValid(String token) {
        try {
            parseToken(token); // if no exception thrown, it's valid
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    // ✅ Internal token parser
    private Jws<Claims> parseToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token);
    }
}
