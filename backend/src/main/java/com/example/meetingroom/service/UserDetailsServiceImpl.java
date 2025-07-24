package com.example.meetingroom.service;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.meetingroom.model.Role;
import com.example.meetingroom.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        /**
         * Spring標準とアプリケーションの両方に
         * User型が存在するので完全修飾クラス名(FQDN)でハンドリングする。
         */
        com.example.meetingroom.entity.User userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("認証失敗です。：" + email));

        UserDetails userDetails = org.springframework.security.core.userdetails.User
                .withUsername(userEntity.getEmail())
                .password(userEntity.getPassword())
                .authorities(getAuthorities(userEntity.isAdmin()))
                .build();

        return userDetails;

    }

    private Collection<? extends GrantedAuthority> getAuthorities(boolean isAdmin) {
        return isAdmin
                ? List.of(new SimpleGrantedAuthority(Role.ROLE_ADMIN.toString()),
                        new SimpleGrantedAuthority(Role.ROLE_USER.toString()))
                : List.of(new SimpleGrantedAuthority(Role.ROLE_USER.toString()));
    }

}
