package com.example.meetingroom.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RoomController {
    @GetMapping("admin/room")
    public String index() {
        return "room/index";
    }

}
