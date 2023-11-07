package com.misijav.flipmemo.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

import static java.time.temporal.ChronoUnit.MINUTES;

@Service
public class JWTUtil {

    @Value("${flipmemo.token.expire.after.minutes}")
   private String expireAfterMinutes;

    private static final String SECRET_KEY =
            "foobar_123456789_foobar_123456789_foobar_123456789_foobar_123456789";

    public String issueToken(
            String subject,
            int tokenVersion) {
        return Jwts
                .builder()
                .setClaims(Map.of("version", tokenVersion))
                .setSubject(subject)
                .setIssuer("localhost")
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(
                        Date.from(
                                Instant.now().plus(Integer.parseInt(expireAfterMinutes), MINUTES)
                        )
                )
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String getSubject(String token) {
        return getClaims(token).getSubject();
    }

    private Claims getClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    public boolean isTokenValid(String jwt, String username, int tokenVersion) {
        Claims claims = getClaims(jwt);
        String subject = claims.getSubject();
        int version = (int) claims.get("version");

        return subject.equals(username) && !isTokenExpired(jwt) && version == tokenVersion;
    }

    private boolean isTokenExpired(String jwt) {
        Date today = Date.from(Instant.now());
        return getClaims(jwt).getExpiration().before(today);
    }
}