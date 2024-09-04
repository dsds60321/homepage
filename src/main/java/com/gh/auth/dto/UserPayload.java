package com.gh.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserPayload implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String id;
    private String email;

    private String nick;

    private String provider; // OAuth 제공자

    private String providerId; // Oauth 제공자 IP

    private String profileImage;


}
