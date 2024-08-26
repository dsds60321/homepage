package com.gh.auth.service;

import com.gh.auth.dto.SignUpRequestDto;
import com.gh.auth.repository.UserRepository;
import com.gh.global.dto.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Repository
@Service
public class AuthService {

    private final UserRepository userRepository;

    public ResponseEntity<?> signUp(SignUpRequestDto dto) {
        String userId = dto.getId();
        boolean isUser = userRepository.existsUserById(userId);

        if (isUser) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.BAD_REQUEST());
        }


        return ResponseEntity.ok("");
    }

}
