package com.gh.auth.provider;

import com.gh.global.dto.response.ApiResponseCode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.function.Function;

@Component
@Slf4j
public class JwtProvider {

    @Value("${jwt.secret}")
    private String SECRET_KEY;

    @Value("${jwt.access-time}")
    private long ACCESS_TIMEOUT;    // 1H

    @Value("${jwt.refresh-time}")
    private long REFRESH_TIMEOUT;   // 7D

    // 토큰에서 사용자 이름 추출
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // 토큰에서 만료일 추출
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // 토큰에서 정보 추출을 위한 범용 메소드
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Key getKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }

    // 토큰에서 정보 추출을 위한 범용 메소드
    private Claims extractAllClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (JwtException e) {
            log.error("JWT parsing error: {}", e.getMessage());
            throw e;
        }
    }

    public Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateAccessToken(String userId) {
        return create(userId, Date.from(Instant.now().plus(ACCESS_TIMEOUT, ChronoUnit.SECONDS)));
    }

    public String generateRefreshToken(String userId) {
        return create(userId, Date.from(Instant.now().plus(REFRESH_TIMEOUT, ChronoUnit.SECONDS)));
    }

    private String create(String userId, Date expiredDate) {
        return Jwts.builder()
                .signWith(getKey())
                .setSubject(userId)
                .setIssuedAt(new Date())
                .setExpiration(expiredDate)
                .compact();
    }

    public String validate(String jwt) {
        if (isTokenExpired(jwt)) {
            throw new JwtException(ApiResponseCode.FORBIDEN.getMessage());
        }
        return extractUsername(jwt);
    }

    public String getRefreshRedisKey(String userId) {
        return userId + "_REFRESH_TOKEN";
    }

    // 추가: 토큰에서 롤(Role) 정보 추출
    public String extractRole(String token) {
        return extractClaim(token, claims -> claims.get("role", String.class));
    }

    // 추가: 토큰 생성 시 롤 정보 포함
    private String create(String userId, String role, Date expiredDate) {
        return Jwts.builder()
                .signWith(getKey())
                .setSubject(userId)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(expiredDate)
                .compact();
    }

    // 롤 정보 포함한 Access Token 생성
    public String generateAccessToken(String userId, String role) {
        return create(userId, role, Date.from(Instant.now().plus(ACCESS_TIMEOUT, ChronoUnit.SECONDS)));
    }
}
