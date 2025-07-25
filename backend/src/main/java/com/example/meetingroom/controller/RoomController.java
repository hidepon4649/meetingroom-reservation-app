package com.example.meetingroom.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.meetingroom.dto.RoomDto;
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

}
