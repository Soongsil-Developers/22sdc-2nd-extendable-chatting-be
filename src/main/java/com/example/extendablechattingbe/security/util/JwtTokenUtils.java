package com.example.extendablechattingbe.security.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

public class JwtTokenUtils {

    public static String generateToken(String username, String key, long expiredTimeMs) {
        Claims claims = Jwts.claims();
        claims.put("username", username);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiredTimeMs))
                .signWith(getSignKey(key), SignatureAlgorithm.HS256)
                .compact();
    }

    private static Key getSignKey(String secretKey) {
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public static boolean isTokenExpired(String token, String key) {
        Date expiredDate = extractClaims(token, key).getExpiration();
        return expiredDate.before(new Date());
    }

    private static Claims extractClaims(String token, String key) {
        return Jwts.parserBuilder().setSigningKey(getSignKey(key))
                .build().parseClaimsJws(token).getBody();
    }

    public static String getUsername(String token, String key) {
        return extractClaims(token, key).get("username", String.class);
    }

}
