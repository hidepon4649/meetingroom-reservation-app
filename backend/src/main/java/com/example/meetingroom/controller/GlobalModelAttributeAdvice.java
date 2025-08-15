package com.example.meetingroom.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.ui.Model;

import com.example.meetingroom.dto.UserDto;
import com.example.meetingroom.model.CustomUserDetails;
import com.example.meetingroom.repository.UserRepository;

@ControllerAdvice
public class GlobalModelAttributeAdvice {
    private final UserRepository userRepository;

    @Value("${app.page-size:20}")
    private int defaultPageSize;

    public GlobalModelAttributeAdvice(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * 表示用ユーザー情報 (UserDto) を共通でModelに追加
     */
    @ModelAttribute("loginUser")
    public UserDto addUserDtoToModel(Principal principal) {
        if (principal == null)
            return null;

        return userRepository.findByEmail(principal.getName())
                .map(user -> {
                    UserDto dto = new UserDto();
                    dto.setName(user.getName()); // 本名やニックネーム
                    return dto;
                })
                .orElse(null);
    }

    /**
     * ログインユーザーIDと管理者権限情報をModelに追加
     */
    @ModelAttribute
    public void addLoginUserInfoToModel(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null && auth.isAuthenticated() && auth.getPrincipal() instanceof CustomUserDetails userDetails) {
            model.addAttribute("loginUserId", userDetails.getId());
            model.addAttribute("isAdmin", userDetails.isAdmin());
        }
    }

    /** 全ページ共通のページサイズ */
    @ModelAttribute("size")
    public int addDefaultPageSize() {
        return defaultPageSize;
    }
}
