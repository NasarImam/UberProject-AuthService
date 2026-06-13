package com.example.Uber_AuthService.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService implements CommandLineRunner {

    @Value("${jwt.expiry}")
    private int expiry;

    @Value("${jwt.secret}")
    private String secret;

    /**
     * Create HMAC signing key from secret
     */
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(
                secret.getBytes(StandardCharsets.UTF_8)
        );
    }

    /**
     * Generate JWT token
     */
    public String createToken(
            Map<String, Object> claims,
            String email
    ) {

        Date now = new Date();

        Date expiryDate = new Date(
                now.getTime() + expiry * 1000L
        );

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(getSigningKey())
                .compact();
    }

    /**
     * Parse token and return all claims
     */
    private Claims extractClaims(String token) {

        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     * Generic claim extractor
     */
    public <T> T extractClaim(
            String token,
            Function<Claims, T> resolver
    ) {

        Claims claims = extractClaims(token);

        return resolver.apply(claims);
    }

    /**
     * Extract subject (email)
     */
    public String extractEmail(String token) {

        return extractClaim(
                token,
                Claims::getSubject
        );
    }

    /**
     * Extract expiry date
     */
    public Date extractExpiration(String token) {

        return extractClaim(
                token,
                Claims::getExpiration
        );
    }

    /**
     * Check if token expired
     */
    public boolean isTokenExpired(String token) {

        return extractExpiration(token)
                .before(new Date());
    }

    /**
     * Validate token
     */
    public boolean validateToken(
            String token,
            String email
    ) {

        String tokenEmail =
                extractEmail(token);

        return tokenEmail.equals(email)
                && !isTokenExpired(token);
    }

    @Override
    public void run(String... args) {

        Map<String, Object> claims =
                new HashMap<>();

        claims.put("email", "a@b.com");
        claims.put("phoneNumber", "9999999997");

        String token =
                createToken(claims, "nasar");

        System.out.println(token);

        System.out.println(
                extractEmail(token)
        );

        System.out.println(
                validateToken(token, "nasar")
        );
    }
}