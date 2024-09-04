package com.gh.auth.dto;

import com.gh.auth.entity.User;
import com.gh.auth.entity.UserRole;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDTO {

    private String userRole;
    private String email;
    private String nick;
    private String username;
    private String password;

    public UserResponseDTO(User entity) {
        this.userRole = entity.getRole().name();
        this.email = entity.getEmail();
        this.nick = entity.getNick();
        this.username = entity.getUsername();
        this.password = entity.getPassword();
    }
}
