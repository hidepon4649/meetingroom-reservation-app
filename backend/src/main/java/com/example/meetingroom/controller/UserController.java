package com.example.meetingroom.controller;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.meetingroom.dto.UserDto;
import com.example.meetingroom.entity.User;
import com.example.meetingroom.service.ReservationService;
import com.example.meetingroom.service.UserService;

@Controller
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;
    private final ReservationService reservationService;

    private final PasswordEncoder passwordEncoder;

    UserController(UserService userService,
            ReservationService reservationService,
            PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.reservationService = reservationService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("admin/user")
    public String index(
            @RequestParam(defaultValue = "0") int page,
            @ModelAttribute("size") int size, // ControllerAdvice で設定された値をそのまま使用
            Model model) {

        Page<User> userPage = userService.getUsersPage(page, size);

        model.addAttribute("users", userPage.getContent());
        model.addAttribute("userPage", userPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", userPage.getTotalPages());
        model.addAttribute("userDto", new UserDto());
        return "user/index";
    }

    @PostMapping("admin/user")
    public String createUser(
            @Validated @ModelAttribute UserDto userDto,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            @RequestParam(defaultValue = "0") int page,
            @ModelAttribute("size") int size,
            Model model) {

        if (bindingResult.hasErrors()) {
            // ページング付きのデータを再取得
            Page<User> userPage = userService.getUsersPage(page, size);
            model.addAttribute("users", userPage.getContent());
            model.addAttribute("userPage", userPage);
            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", userPage.getTotalPages());
            model.addAttribute("size", size);

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

        // 現在のページ番号とサイズをURLパラメータとして付与してリダイレクト
        return "redirect:/admin/user?page=" + page + "&size=" + size;
    }

    @PostMapping(value = "/admin/user/{id}", params = "_method=update")
    public String updateUser(@PathVariable Long id,
            @Validated @ModelAttribute UserDto userDto,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            @RequestParam(defaultValue = "0") int page,
            @ModelAttribute("size") int size,
            Model model) {

        if (bindingResult.hasErrors()) {
            // 画像がある場合は、再表示用にモデルに追加
            model.addAttribute("userPicture", userService.findById(id).getPicture());
            model.addAttribute("currentPage", page);
            model.addAttribute("size", size);

            return "user/edit";
        }

        User user = userService.findById(id);
        user.setEmail(userDto.getEmail());
        user.setAdmin(userDto.isAdmin());
        user.setName(userDto.getName());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setTel(userDto.getTel());
        user.setDepartment(userDto.getDepartment());

        // 新たな画像がアップロードされた場合だけ上書き
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
        redirectAttributes.addFlashAttribute("successMessage", "ユーザを編集しました。");

        return "redirect:/admin/user?page=" + page + "&size=" + size;
    }

    @GetMapping(value = "/admin/user/{id}")
    public String updateUser(@PathVariable Long id,
            Model model,
            @RequestParam(defaultValue = "0") int page,
            @ModelAttribute("size") int size) {

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
        model.addAttribute("userPicture", user.getPicture());
        model.addAttribute("currentPage", page);
        model.addAttribute("size", size);

        return "user/edit";
    }

    @PostMapping(value = "/admin/user/{id}", params = "_method=delete")
    public String deleteUser(@PathVariable Long id, RedirectAttributes redirectAttributes,
            @RequestParam(defaultValue = "0") int page,
            @ModelAttribute("size") int size) {

        // 予約があるかチェック
        if (reservationService.existsByUserId(id)) {
            redirectAttributes.addFlashAttribute("errorMessage", "予約が存在するため、ユーザを削除できません。");
            return "redirect:/admin/user?page=" + page + "&size=" + size;
        }

        userService.deleteById(id);
        redirectAttributes.addFlashAttribute("successMessage", "ユーザを削除しました。");
        return "redirect:/admin/user?page=" + page + "&size=" + size;
    }

}
