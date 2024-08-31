package com.gh.auth.dto;

import com.gh.auth.entity.Auth;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponseDTO {

    private String tokenType;
    private String accessToken;
    private String refreshToken;

    @Builder
    public AuthResponseDTO(Auth entity) {
        this.tokenType = entity.getTokenType();
        this.accessToken = entity.getAccessToken();
        this.refreshToken = entity.getRefreshToken();
    }
}
