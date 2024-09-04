package com.gh.auth.service;

import com.gh.auth.dto.CustomUserDetail;
import com.gh.auth.entity.User;
import com.gh.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("해당 유저가 존재하지 않습니다."));
        return new CustomUserDetail(user);
    }

    public UserDetails loadUserById(Long userId) throws IllegalArgumentException {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UsernameNotFoundException("해당 유저가 존재하지 않습니다."));
        return new CustomUserDetail(user);
    }

    public UserDetails loadUserByEmail(String email) throws IllegalArgumentException {
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("해당 유저가 존재하지 않습니다."));
        return new CustomUserDetail(user);
    }
}
