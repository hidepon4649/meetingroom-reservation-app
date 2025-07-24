package com.example.meetingroom.controller;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.meetingroom.dto.UserDto;
import com.example.meetingroom.entity.User;
import com.example.meetingroom.service.UserService;

@Controller
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("admin/user")
    public String index(Model model) {

        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("userDto", new UserDto());
        return "user/index";
    }

    @PostMapping("admin/user")
    public String createUser(
            @Validated @ModelAttribute UserDto userDto,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("users", userService.getAllUsers()); // 再表示用
            return "user/index";
        }

        User newUser = new User();
        newUser.setEmail(userDto.getEmail());
        newUser.setAdmin(userDto.isAdmin());
        newUser.setName(userDto.getName());
        newUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
        newUser.setTel(userDto.getTel());
        newUser.setDepartment(userDto.getDepartment());

        MultipartFile picture = userDto.getPicture();
        if (picture != null && !picture.isEmpty()) {
            try {
                newUser.setPicture(picture.getBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }

        userService.save(newUser);

        // トースト表示用に成功メッセージをフラッシュスコープに保存
        redirectAttributes.addFlashAttribute("successMessage", "ユーザを登録しました");

        return "redirect:/admin/user";
    }

    @PostMapping(value = "/admin/user/{id}", params = "_method=update")
    public String updateUser(@PathVariable Long id,
            @Validated @ModelAttribute UserDto userDto,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            Model model) {

        if (bindingResult.hasErrors()) {
            return "user/edit";
        }

        User user = new User();
        user.setId(userDto.getId());
        user.setEmail(userDto.getEmail());
        user.setAdmin(userDto.isAdmin());
        user.setName(userDto.getName());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setTel(userDto.getTel());
        user.setDepartment(userDto.getDepartment());

        MultipartFile picture = userDto.getPicture();
        if (picture != null && !picture.isEmpty()) {
            try {
                user.setPicture(picture.getBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        userService.save(user);

        // トースト表示用に成功メッセージをフラッシュスコープに保存
        redirectAttributes.addFlashAttribute("successMessage", "ユーザーを編集しました。");

        return "redirect:/admin/user";
    }

    @GetMapping(value = "/admin/user/{id}")
    public String updateUser(@PathVariable Long id,
            Model model) {

        User user = userService.findById(id);

        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setAdmin(user.isAdmin());
        userDto.setName(user.getName());
        userDto.setPassword(user.getPassword());
        userDto.setTel(user.getTel());
        userDto.setDepartment(user.getDepartment());

        model.addAttribute("userDto", userDto);

        return "user/edit";
    }

    @PostMapping(value = "/admin/user/{id}", params = "_method=delete")
    public String deleteUser(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        userService.deleteById(id);
        redirectAttributes.addFlashAttribute("successMessage", "ユーザーを削除しました。");
        return "redirect:/admin/user";
    }

}
