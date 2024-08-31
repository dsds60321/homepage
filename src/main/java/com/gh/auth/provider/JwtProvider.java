package com.gh.auth.provider;

import com.gh.auth.dto.CustomUserDetail;
import io.jsonwebtoken.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.SignatureException;
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

    public String generateAccessToken(Authentication authentication) {
        CustomUserDetail customUserDetails = (CustomUserDetail) authentication.getPrincipal();
        Date expiryDate = Date.from(Instant.now().plus(ACCESS_TIMEOUT, ChronoUnit.SECONDS));
        return Jwts.builder()
                .setSubject(customUserDetails.getUsername())
                .claim("user-id", customUserDetails.getId())
                .claim("user-email", customUserDetails.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    public String generateRefreshToken(Authentication authentication) {
        CustomUserDetail customUserDetails = (CustomUserDetail) authentication.getPrincipal();
        Date expiryDate = Date.from(Instant.now().plus(REFRESH_TIMEOUT, ChronoUnit.SECONDS));
        return Jwts.builder()
                .setSubject(customUserDetails.getUsername())
                .claim("user-id", customUserDetails.getId())
                .claim("user-email", customUserDetails.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public Long getUserIdFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .get("user-id", Long.class);
    }

    public String getUsernameFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public String getUserEmailFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .get("user-email", String.class);
    }

    public Date getExpirationFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public Boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException ex) {
            System.out.println("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            System.out.println("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            System.out.println("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            System.out.println("JWT claims string is empty.");
        }
        return false;
    }

    public String getToken(String refreshToken) {

        if (StringUtils.hasText(refreshToken) && refreshToken.length() > 7) {
            return refreshToken.substring(7).trim();
        }

        return null;
    }
}
