
package com.stockmanagement.server;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

import org.springframework.stereotype.Service;

@Service
public class JwtService {

    // Development key only. Replace with a securely generated secret
    // stored outside source control before deployment.
    private static final String SECRET =
            "ReplaceThisWithASecretKeyAtLeast32BytesLong!";

    private final SecretKey key = Keys.hmacShaKeyFor(
            SECRET.getBytes(StandardCharsets.UTF_8)
    );

    private static final long EXPIRATION_TIME = 1000L * 60 * 60;

    public String generateToken(String username, String role) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + EXPIRATION_TIME);

        return Jwts.builder()
                .subject(username)
                .claim("role", role)
                .issuedAt(now)
                .expiration(expiry)
                .signWith(key)
                .compact();
    }

    public Claims extractClaims(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean isTokenValid(String token, String username) {
        try {
            Claims claims = extractClaims(token);

            return username.equals(claims.getSubject())
                    && claims.getExpiration().after(new Date());

        } catch (Exception e) {
            return false;
        }
    }
}
