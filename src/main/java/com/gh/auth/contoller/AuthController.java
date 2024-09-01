package com.gh.auth.contoller;

import com.gh.auth.dto.AuthRequestDTO;
import com.gh.auth.dto.UserRequestDTO;
import com.gh.auth.service.AuthService;
import com.gh.global.dto.response.ApiResponse;
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
    public ResponseEntity<?> singUp(@RequestBody UserRequestDTO userRequestDTO) {
        return authService.signup(userRequestDTO);
    }

    /**
     * 로그인
     */
    @PostMapping("/sign-in")
    public ResponseEntity<?> singIn(@RequestBody AuthRequestDTO authRequestDTO) {
        return authService.signIn(authRequestDTO);
    }

    @PostMapping("/user")
    public ResponseEntity<?> accessToken(@RequestHeader("Authorization") String accessToken) {
        return authService.accessToken(accessToken);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestHeader("Authorization") String refreshToken) {
        return authService.refreshToken(refreshToken);
    }

}
