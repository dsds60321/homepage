package com.gh.auth.dto;

import com.gh.auth.entity.User;
import com.gh.auth.entity.UserRole;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDTO {

    private UserRole userRole;
    @NotBlank
    private String email;
    @NotBlank
    private String nick;
    @NotBlank
    private String username;
    @NotBlank
    private String password;

    public User toEntity() {
        return User.builder()
                .role(this.userRole)
                .email(this.email)
                .username(this.username)
                .password(this.password)
                .nick(this.nick)
                .build();
    }

}
