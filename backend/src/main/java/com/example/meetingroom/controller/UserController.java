package com.example.meetingroom.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
        return "user/index";
    }

    @PostMapping("admin/user")
    public String createUser(
            User user,
            @RequestParam("photo") MultipartFile photo,
            RedirectAttributes redirectAttributes) {

        user.setPassword("pasword");// 初期パスワード
        userService.save(user);

        // トースト表示用に成功メッセージをフラッシュスコープに保存
        redirectAttributes.addFlashAttribute("successMessage", "ユーザを登録しました");

        return "redirect:/admin/user";
    }

    @PostMapping(value = "/admin/user", params = "_method=delete")
    public String deleteUser(@RequestParam("id") Long id, RedirectAttributes redirectAttributes) {
        userService.deleteById(id);
        redirectAttributes.addFlashAttribute("successMessage", "ユーザーを削除しました。");
        return "redirect:/admin/user";
    }

}
