package com.gh.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignUpRequestDto {

    @NotBlank
    @Pattern(regexp = "^[A-Za-z\\d]{6,20}$")
    private String id;

    @NotBlank
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*&])[A-Za-z\\d!@#$%^&*&]{8,15}$")
    private String password;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String certificationNumber;
}
