package com.gh.auth.contoller;

import com.gh.auth.dto.AuthRequestDTO;
import com.gh.auth.dto.EmailAuthRequestDTO;
import com.gh.auth.dto.TempMailRequestDTO;
import com.gh.auth.dto.UserRequestDTO;
import com.gh.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    /**
     * 로그인
     */
    @PostMapping("/sign-up")
    public ResponseEntity<?> singUp(@RequestBody @Valid UserRequestDTO userRequestDTO) {
        return authService.signup(userRequestDTO);
    }

    /**
     * 로그인
     */
    @PostMapping("/sign-in")
    public ResponseEntity<?> singIn(@RequestBody AuthRequestDTO authRequestDTO) {
        return authService.signIn(authRequestDTO);
    }

    // 이메일 발송
    @PostMapping("/email")
    public ResponseEntity<?> tempMail(@RequestBody TempMailRequestDTO tempMailRequestDTO) {
        return authService.sendTempMail(tempMailRequestDTO);
    }

    // 이메일 2차 인증 검증
    @PostMapping("/email/verify")
    public ResponseEntity<?> tempMail(@RequestBody @Valid EmailAuthRequestDTO emailAuthRequestDTO) {
        return authService.verifyEmailAuth(emailAuthRequestDTO);
    }

    // user 토큰 값 가져오기
    @PostMapping("/user")
    public ResponseEntity<?> accessToken(@RequestHeader("Authorization") String accessToken) {
        return authService.accessToken(accessToken);
    }

    // 토큰 리프리쉬
    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestHeader("Authorization") String refreshToken) {
        return authService.refreshToken(refreshToken);
    }

}
