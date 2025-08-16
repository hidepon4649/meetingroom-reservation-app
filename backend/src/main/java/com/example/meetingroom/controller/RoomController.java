package com.example.meetingroom.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.meetingroom.dto.RoomDto;
import com.example.meetingroom.entity.Room;
import com.example.meetingroom.service.ReservationService;
import com.example.meetingroom.service.RoomService;

@Controller
public class RoomController {

    private static final Logger logger = LoggerFactory.getLogger(RoomController.class);

    private final RoomService roomService;
    private final ReservationService reservationService;

    RoomController(RoomService roomService,
            ReservationService reservationService) {
        this.roomService = roomService;
        this.reservationService = reservationService;
    }

    @GetMapping("admin/room")
    public String index(
            @RequestParam(defaultValue = "0") int page,
            @ModelAttribute("size") int size, // ControllerAdvice で設定された値をそのまま使用
            Model model) {

        Page<Room> roomPage = roomService.getRoomsPage(page, size);

        model.addAttribute("rooms", roomPage.getContent());
        model.addAttribute("roomPage", roomPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", roomPage.getTotalPages());
        model.addAttribute("roomDto", new RoomDto());
        return "room/index";
    }

    @PostMapping("admin/room")
    public String createRoom(
            @Validated @ModelAttribute RoomDto roomDto,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            @RequestParam(defaultValue = "0") int page,
            @ModelAttribute("size") int size,
            Model model) {

        if (bindingResult.hasErrors()) {
            // ページング付きのデータを再取得
            Page<Room> roomPage = roomService.getRoomsPage(page, size);
            model.addAttribute("rooms", roomPage.getContent());
            model.addAttribute("roomPage", roomPage);
            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", roomPage.getTotalPages());
            model.addAttribute("size", size);

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

        // 現在のページ番号とサイズをURLパラメータとして付与してリダイレクト
        return "redirect:/admin/room?page=" + page + "&size=" + size;
    }

    @PostMapping(value = "/admin/room/{id}", params = "_method=update")
    public String updateRoom(@PathVariable Long id,
            @Validated @ModelAttribute RoomDto roomDto,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            @RequestParam(defaultValue = "0") int page,
            @ModelAttribute("size") int size,
            Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("currentPage", page);
            model.addAttribute("size", size);

            return "room/edit";
        }

        Room room = roomService.findById(id);
        room.setName(roomDto.getName());
        room.setPlace(roomDto.getPlace());
        room.setTel(roomDto.getTel());
        room.setRemarks(roomDto.getRemarks());

        roomService.save(room);

        // トースト表示用に成功メッセージをフラッシュスコープに保存
        redirectAttributes.addFlashAttribute("successMessage", "会議室を編集しました。");

        return "redirect:/admin/room?page=" + page + "&size=" + size;
    }

    @GetMapping(value = "/admin/room/{id}")
    public String updateRoom(@PathVariable Long id,
            Model model,
            @RequestParam(defaultValue = "0") int page,
            @ModelAttribute("size") int size) {

        Room room = roomService.findById(id);

        RoomDto roomDto = new RoomDto();
        roomDto.setId(room.getId());
        roomDto.setName(room.getName());
        roomDto.setPlace(room.getPlace());
        roomDto.setTel(room.getTel());
        roomDto.setRemarks(room.getRemarks());

        model.addAttribute("roomDto", roomDto);
        model.addAttribute("currentPage", page);
        model.addAttribute("size", size);

        return "room/edit";
    }

    @PostMapping(value = "/admin/room/{id}", params = "_method=delete")
    public String deleteRoom(@PathVariable Long id, RedirectAttributes redirectAttributes,
            @RequestParam(defaultValue = "0") int page,
            @ModelAttribute("size") int size) {

        // 予約があるかチェック
        if (reservationService.existsByRoomId(id)) {
            redirectAttributes.addFlashAttribute("errorMessage", "予約が存在するため、会議室を削除できません。");
            return "redirect:/admin/room?page=" + page + "&size=" + size;
        }

        roomService.deleteById(id);
        redirectAttributes.addFlashAttribute("successMessage", "会議室を削除しました。");
        return "redirect:/admin/room?page=" + page + "&size=" + size;
    }

}
