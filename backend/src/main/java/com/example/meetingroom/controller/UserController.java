package com.example.meetingroom.controller;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("admin/user")
    public String index(Model model) {

        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("user", new User());
        return "user/index";
    }

    @PostMapping("admin/user")
    public String createUser(
            @Validated @ModelAttribute UserDto dto,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("users", userService.getAllUsers()); // 再表示用
            return "user/index";
        }

        User user = new User();
        user.setEmail(dto.getEmail());
        user.setAdmin(dto.isAdmin());
        user.setName(dto.getName());
        user.setPassword("password"); // 仮パスワード
        user.setTel(dto.getTel());
        user.setDepartment(dto.getDepartment());

        MultipartFile picture = dto.getPicture();
        if (picture != null && !picture.isEmpty()) {
            try {
                user.setPicture(picture.getBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }

        userService.save(user);

        // トースト表示用に成功メッセージをフラッシュスコープに保存
        redirectAttributes.addFlashAttribute("successMessage", "ユーザを登録しました");

        return "redirect:/admin/user";
    }

    @PostMapping(value = "/admin/user/{id}", params = "_method=update")
    public String updateUser(@PathVariable Long id,
            @Validated @ModelAttribute User user,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("users", userService.getAllUsers()); // 再表示用
            return "user/index";
        }

        user.setPassword("pasword");// ダミー

        userService.save(user);

        // トースト表示用に成功メッセージをフラッシュスコープに保存
        redirectAttributes.addFlashAttribute("successMessage", "ユーザーを編集しました。");

        return "redirect:/admin/user";
    }

    @GetMapping(value = "/admin/user/{id}")
    public String updateUser(@PathVariable Long id,
            Model model) {

        User user = userService.findById(id);
        model.addAttribute("user", user);

        return "user/edit";
    }

    @PostMapping(value = "/admin/user/{id}", params = "_method=delete")
    public String deleteUser(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        userService.deleteById(id);
        redirectAttributes.addFlashAttribute("successMessage", "ユーザーを削除しました。");
        return "redirect:/admin/user";
    }

}
