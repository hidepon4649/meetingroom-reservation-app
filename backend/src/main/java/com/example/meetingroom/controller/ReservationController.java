package com.example.meetingroom.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.meetingroom.dto.ReservationDto;
import com.example.meetingroom.service.ReservationService;

@Controller
public class ReservationController {

    private static final Logger logger = LoggerFactory.getLogger(ReservationController.class);

    private final ReservationService reservationService;

    ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("reservation")
    public String index(Model model) {

        model.addAttribute("reservations", reservationService.getAllReservations());
        model.addAttribute("reservationDto", new ReservationDto());
        return "reservation/index";
    }

    @PostMapping(value = "/reservation/{id}", params = "_method=delete")
    public String deleteReservation(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        reservationService.deleteById(id);
        redirectAttributes.addFlashAttribute("successMessage", "会議室予約を削除しました。");
        return "redirect:/reservation";
    }

}
