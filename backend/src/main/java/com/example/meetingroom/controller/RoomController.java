package com.example.meetingroom.controller;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.meetingroom.dto.RoomDto;
import com.example.meetingroom.entity.Room;
import com.example.meetingroom.service.RoomService;

@Controller
public class RoomController {

    private static final Logger logger = LoggerFactory.getLogger(RoomController.class);

    private final RoomService roomService;

    RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping("admin/room")
    public String index(Model model) {
        model.addAttribute("rooms", roomService.getAllRooms());
        model.addAttribute("roomDto", new RoomDto());
        return "room/index";
    }

    @PostMapping("admin/room")
    public String createRoom(
            @Validated @ModelAttribute RoomDto roomDto,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("rooms", roomService.getAllRooms()); // 再表示用
            return "room/index";
        }

        Room newRoom = new Room();
        newRoom.setName(roomDto.getName());
        newRoom.setPlace(roomDto.getPlace());
        newRoom.setTel(roomDto.getTel());
        newRoom.setRemarks(roomDto.getRemarks());

        roomService.save(newRoom);

        // トースト表示用に成功メッセージをフラッシュスコープに保存
        redirectAttributes.addFlashAttribute("successMessage", "会議室を登録しました");

        return "redirect:/admin/room";
    }

    @PostMapping(value = "/admin/room/{id}", params = "_method=update")
    public String updateRoom(@PathVariable Long id,
            @Validated @ModelAttribute RoomDto roomDto,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            Model model) {

        if (bindingResult.hasErrors()) {
            return "room/edit";
        }

        Room room = new Room();
        room.setId(roomDto.getId());
        room.setName(roomDto.getName());
        room.setPlace(roomDto.getPlace());
        room.setTel(roomDto.getTel());
        room.setRemarks(roomDto.getRemarks());

        roomService.save(room);

        // トースト表示用に成功メッセージをフラッシュスコープに保存
        redirectAttributes.addFlashAttribute("successMessage", "会議室を編集しました。");

        return "redirect:/admin/room";
    }

    @GetMapping(value = "/admin/room/{id}")
    public String updateRoom(@PathVariable Long id,
            Model model) {

        Room room = roomService.findById(id);

        RoomDto roomDto = new RoomDto();
        roomDto.setId(room.getId());
        roomDto.setName(room.getName());
        roomDto.setPlace(room.getPlace());
        roomDto.setTel(room.getTel());
        roomDto.setRemarks(room.getRemarks());

        model.addAttribute("roomDto", roomDto);

        return "room/edit";
    }

    @PostMapping(value = "/admin/room/{id}", params = "_method=delete")
    public String deleteRoom(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        roomService.deleteById(id);
        redirectAttributes.addFlashAttribute("successMessage", "会議室を削除しました。");
        return "redirect:/admin/room";
    }

}
