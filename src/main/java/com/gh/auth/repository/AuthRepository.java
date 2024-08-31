package com.gh.auth.repository;

import com.gh.auth.entity.Auth;
import com.gh.auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthRepository extends JpaRepository<Auth, Long> {
    boolean existsByUser(User user);
    Optional<Auth> findByRefreshToken(String refreshToken);
}
