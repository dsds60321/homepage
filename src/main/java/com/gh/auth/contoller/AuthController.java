package com.gh.auth.contoller;

import com.gh.auth.dto.SignUpRequestDto;
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

    @GetMapping("/session")
    public ResponseEntity<?> session() {
        return ResponseEntity.ok("hihi");
    }

    /**
     * 회원가입
     */
    @PostMapping("/sign-up")
    public ResponseEntity<?> singUp(@RequestBody SignUpRequestDto requestDto) {
        return authService.signUp(requestDto);
    }
}
