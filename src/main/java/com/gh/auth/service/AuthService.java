package com.gh.auth.service;

import com.gh.auth.dto.AuthRequestDTO;
import com.gh.auth.dto.CustomUserDetail;
import com.gh.auth.dto.UserRequestDTO;
import com.gh.auth.entity.Auth;
import com.gh.auth.entity.User;
import com.gh.auth.entity.UserRole;
import com.gh.auth.provider.JwtProvider;
import com.gh.auth.repository.AuthRepository;
import com.gh.auth.repository.UserRepository;
import com.gh.global.dto.response.ApiResponse;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Service
public class AuthService {

    private final UserRepository userRepository;
    private final AuthRepository authRepository;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;


    /** 회원가입 */
    @Transactional
    public ResponseEntity<?> signup(UserRequestDTO requestDto) {
        // SAVE USER ENTITY
        requestDto.setUserRole(UserRole.ROLE_USER);
        requestDto.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        this.userRepository.save(requestDto.toEntity());

        return ApiResponse.SUCCESS(requestDto);
    }

    // 로그인
    @Transactional
    public ResponseEntity<?> signIn(AuthRequestDTO requestDTO) {
        Optional<User> optionalUser = userRepository.findByUsername(requestDTO.getUsername());

        if (!optionalUser.isPresent()) {
            return ApiResponse.BAD_REQUEST("아이디 또는 비밀번호가 잘못 되었습니다. 아이디와 비밀번호를 정확히 입력해주세요.");
        }

        User user = optionalUser.get();

        if (!passwordEncoder.matches(requestDTO.getPassword(), user.getPassword())) {
            return ApiResponse.BAD_REQUEST("아이디 또는 비밀번호가 잘못 되었습니다. 아이디와 비밀번호를 정확히 입력해주세요.");
        }


        String accessToken = jwtProvider.generateAccessToken(new UsernamePasswordAuthenticationToken(new CustomUserDetail(user), user.getPassword()));
        String refreshToken = jwtProvider.generateRefreshToken(new UsernamePasswordAuthenticationToken(new CustomUserDetail(user), user.getPassword()));

        if (authRepository.existsByUser(user)) {
            user.getAuth().updateAccessToken(accessToken);
            user.getAuth().updateRefreshToken(accessToken);
            return ApiResponse.SUCCESS(user.getAuth());
        }

        Auth auth = authRepository.save(Auth.builder()
                .user(user)
                .tokenType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build());

        return ApiResponse.SUCCESS(auth);
    }

    /** Token 갱신 */
    @Transactional
    public ResponseEntity<ApiResponse> refreshToken(String refreshToken) {
        String token = jwtProvider.getParserToken(refreshToken);

        if (token == null) {
            log.info("유효하지 않은 토큰 : {} " , refreshToken);
            return ApiResponse.EXPIRED_TOKEN();
        }

        if (jwtProvider.validateToken(refreshToken)) {
            Auth auth = this.authRepository.findByRefreshToken(refreshToken).orElseThrow(
                    () -> new IllegalArgumentException("해당 REFRESH_TOKEN 을 찾을 수 없습니다.\nREFRESH_TOKEN = " + refreshToken));

            String newAccessToken = jwtProvider.generateAccessToken(
                    new UsernamePasswordAuthenticationToken(
                            new CustomUserDetail(auth.getUser()), auth.getUser().getPassword()));

            auth.updateAccessToken(newAccessToken);
            return ApiResponse.SUCCESS(newAccessToken);
        }

        return ApiResponse.EXPIRED_TOKEN();
    }

    public ResponseEntity<?> accessToken(String accessToken) {
        String token = jwtProvider.getParserToken(accessToken);
        Boolean isValid = jwtProvider.validateToken(token);

        if (isValid) {
            Claims claim = jwtProvider.getUserFromToken(token);
            System.out.println(claim);
            return ApiResponse.SUCCESS(claim);
        }



        return ApiResponse.EXPIRED_TOKEN();
    }

}
