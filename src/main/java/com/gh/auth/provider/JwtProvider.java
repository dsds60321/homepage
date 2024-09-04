package com.gh.auth.provider;

import com.gh.auth.dto.CustomUserDetail;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Component
@Slf4j
public class JwtProvider {

    @Value("${jwt.secret}")
    private String SECRET_KEY;

    @Value("${jwt.access-time}")
    private long ACCESS_TIMEOUT;    // 1H

    @Value("${jwt.refresh-time}")
    private long REFRESH_TIMEOUT;   // 7D

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    private Claims parseClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (JwtException e) {
            log.error("JWT parsing error: {}", e.getMessage());
            throw new RuntimeException("JWT parsing error", e);
        }
    }

    public String generateToken(Authentication authentication, long timeout) {
        CustomUserDetail userDetails = (CustomUserDetail) authentication.getPrincipal();
        Date expiryDate = Date.from(Instant.now().plus(timeout, ChronoUnit.SECONDS));

        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .claim("id"     , userDetails.getId())
                .claim("userId" , userDetails.getUsername())
                .claim("email"  , userDetails.getEmail())
                .claim("nick"   , userDetails.getNick())
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateAccessToken(Authentication authentication) {
        return generateToken(authentication, ACCESS_TIMEOUT);
    }

    public String generateRefreshToken(Authentication authentication) {
        return generateToken(authentication, REFRESH_TIMEOUT);
    }

    public Claims getUserFromToken(String token) {
        return parseClaims(token);
    }

    public Long getUserIdFromToken(String token) {
        return parseClaims(token).get("id", Long.class);
    }

    public String getUsernameFromToken(String token) {
        return parseClaims(token).getSubject();
    }

    public String getUserEmailFromToken(String token) {
        return parseClaims(token).get("email", String.class);
    }

    public Date getExpirationFromToken(String token) {
        return parseClaims(token).getExpiration();
    }

    public Boolean validateToken(String token) {
        try {
            parseClaims(token); // This will throw an exception if the token is invalid
            return true;
        } catch (RuntimeException e) {
            log.warn("JWT validation failed: {}", e.getMessage());
            return false;
        }
    }

    public String getParserToken(String refreshToken) {
        if (StringUtils.hasText(refreshToken) && refreshToken.length() > 7) {
            return refreshToken.substring(7).trim();
        }
        return null;
    }
}