package com.payzo.user_service.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;


import java.security.Key;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

public class JWTUtil {

    private static final String SECRET = "secret123secret123secret123";

    private Key getSigningKey() {
        // Use UTF-8 to avoid encoding issues
        return Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
    }

    public String extractEmail(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject(); // "subject" usually stores email/username
    }

    public boolean validateToken(String token, String username){
        try {
            extractEmail(token); // if parsing suceeds, token is valid
            return true;
        } catch (Exception e) { // if token in valid
            return  false;
        }
    }

    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject(); // "subject" usually stores email/username
    }

    public String generateToken(Map<String, Object> claims, String email){
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 1 day
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact(); // build
    }
}
