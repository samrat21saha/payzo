package com.payzo.user_service.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.nio.charset.StandardCharsets;

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
}
