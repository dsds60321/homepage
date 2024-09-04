package com.gh.auth.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gh.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import java.util.Objects;

@Entity(name = "user")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
@Getter
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, length = 100)
    private String password;

    private String email;

    private String nick;

    private String provider; // OAuth 제공자

    private String providerId; // Oauth 제공자 IP

    private String profileImage;

    @Enumerated(EnumType.STRING)
    private UserRole role;


    @OneToOne(mappedBy = "user", cascade = CascadeType.REMOVE)
    @JsonIgnore
    private Auth auth;

    @Builder
    public User(String username, String password, String email, String nick, String provider, String providerId, UserRole role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.nick = nick;
        this.provider = provider;
        this.providerId = providerId;
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
