package com.gh.auth.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import java.util.Objects;

@Entity(name = "users")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    private String id;

    private String email;

    private String nick;

    private String provider; // OAuth 제공자

    private String providerId; // Oauth 제공자 IP

    private String profileImage;



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(idx, user.idx);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idx);
    }
}
