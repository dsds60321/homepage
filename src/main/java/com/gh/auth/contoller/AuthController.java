package com.gh.auth.contoller;

import com.gh.auth.dto.SignInReqDto;
import com.gh.auth.dto.SignUpReqDto;
import com.gh.auth.dto.TokenRequestDto;
import com.gh.auth.service.AuthService;
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

    @PostMapping("/session")
    public ResponseEntity<?> session(@RequestBody TokenRequestDto tokenRequestDto) {
        return authService.getSession(tokenRequestDto);
    }

    /**
     * 회원가입
     */
    @PostMapping("/sign-up")
    public ResponseEntity<?> singUp(@RequestBody SignUpReqDto requestDto) {
        return authService.signUp(requestDto);
    }

    /**
     * 로그인
     */
    @PostMapping("/sign-in")
    public ResponseEntity<?> singIn(@RequestBody SignInReqDto requestDto) {
        return authService.signIn(requestDto);
    }
}
