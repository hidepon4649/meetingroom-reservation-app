package com.example.meetingroom.controller;

import java.security.Principal;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.example.meetingroom.dto.UserDto;
import com.example.meetingroom.repository.UserRepository;

@ControllerAdvice
public class GlobalModelAttributeAdvice {
    private final UserRepository userRepository;

    public GlobalModelAttributeAdvice(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

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
}
