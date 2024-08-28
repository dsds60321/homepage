package com.gh.auth.service;

import com.gh.auth.dto.SignInReqDto;
import com.gh.auth.dto.SignUpReqDto;
import com.gh.auth.dto.TokenRequestDto;
import com.gh.auth.dto.TokenResponseDto;
import com.gh.auth.entity.UserRole;
import com.gh.auth.provider.JwtProvider;
import com.gh.auth.repository.UserRepository;
import com.gh.global.dto.response.ApiResponse;
import com.gh.global.util.CommonUtil;
import com.gh.global.util.DateUtil;
import com.gh.global.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Slf4j
@Service
public class AuthService {

    @Value("${jwt.refresh-time}")
    private long REFRESH_TIMEOUT;   // 7D

    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;
    private final RedisUtil redisUtil;
    private final StringRedisTemplate stringRedisTemplate;
    private static final String GUEST_REDIS_KEY = "%s_%s_%s";

    public ResponseEntity<?> signUp(SignUpReqDto dto) {
        String userId = dto.getId();
        boolean isUser = userRepository.existsUserById(userId);

        if (isUser) {
            return ApiResponse.BAD_REQUEST();
        }


        return ResponseEntity.ok("");
    }

    // 로그인
    public ResponseEntity<?> signIn(SignInReqDto requestDto) {
        UserRole role = requestDto.getUserRole();
        if (role.equals(UserRole.GUEST)) {
            log.info("GUEST_LOGIN : {} ", role);
            // 중복되지 않기 위해 token명 생성

            String guestId =String.format(GUEST_REDIS_KEY, role.name(), DateUtil.getDate("yyyyMMdd"), CommonUtil.randomUUid(7));
            // TOKEN 생성
            String accessToken = jwtProvider.generateAccessToken(guestId);
            String refreshToken = jwtProvider.getRefreshRedisKey(guestId);
            stringRedisTemplate.opsForValue().set(jwtProvider.getRefreshRedisKey(guestId), refreshToken, REFRESH_TIMEOUT , TimeUnit.SECONDS);

            redisUtil.set(guestId, "GUEST", Duration.ofSeconds(REFRESH_TIMEOUT));
            return ApiResponse.SUCCESS(new TokenResponseDto(accessToken, refreshToken));
        }

        log.info("role : {} ", role);


        return ApiResponse.BAD_REQUEST();
    }

    public ResponseEntity<?> getSession(TokenRequestDto tokenRequestDto) {
        String accessToken = tokenRequestDto.getAccessToken();
        String refreshToken = tokenRequestDto.getRefreshToken();

        Boolean isExpired = jwtProvider.isTokenExpired(refreshToken);

        if (isExpired) {
            return ApiResponse.EXPIRED_TOKEN();
        }

        if (!StringUtils.hasText(accessToken)) {
            return ApiResponse.FORBIDEN();
        }


        return ApiResponse.SUCCESS(new TokenResponseDto(accessToken, refreshToken));
    }
}
