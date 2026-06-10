package com.example.Uber_AuthService.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService implements CommandLineRunner {

    @Value("${jwt.expiry}")
    private int expiry;

    @Value("${jwt.secret}")
    private String SECRET;

    public String createToken(Map<String, Object> payLoad, String userName) {

        Date date = new Date();
        Date expiryDate = new Date(date.getTime() + expiry * 1000L);

        SecretKey key = Keys.hmacShaKeyFor(
                SECRET.getBytes(StandardCharsets.UTF_8)
        );

        return Jwts.builder()
                .setClaims(payLoad)
                .setIssuedAt(date)
                .setExpiration(expiryDate)
                .setSubject(userName)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public void run(String... args) {

        Map<String, Object> mp = new HashMap<>();
        mp.put("email", "a@b.com");
        mp.put("phonenumber", "9999999997");

        String result = createToken(mp, "nasar");

        System.out.println(result);
    }
}