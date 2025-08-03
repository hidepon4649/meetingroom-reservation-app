package com.example.meetingroom.controller;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

import com.example.meetingroom.dto.ReservationDto;
import com.example.meetingroom.entity.Reservation;
import com.example.meetingroom.service.ReservationService;
import com.example.meetingroom.service.RoomService;
import com.example.meetingroom.service.UserService;

@Controller
public class ReservationController {

    private static final Logger logger = LoggerFactory.getLogger(ReservationController.class);

    private final ReservationService reservationService;
    private final RoomService roomService;
    private final UserService userService;

    ReservationController(
            ReservationService reservationService,
            RoomService roomService,
            UserService userService) {
        this.reservationService = reservationService;
        this.roomService = roomService;
        this.userService = userService;
    }

    @GetMapping("reservation")
    public String index(Model model) {

        model.addAttribute("reservations", reservationService.getAllReservations());
        model.addAttribute("reservationDto", new ReservationDto());

        model.addAttribute("rooms", roomService.getAllRooms());
        model.addAttribute("users", userService.getAllUsers());

        return "reservation/index";
    }

    @PostMapping("reservation")
    public String createReservation(
            @Validated @ModelAttribute ReservationDto reservationDto,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("reservations", reservationService.getAllReservations()); // 再表示用
            model.addAttribute("rooms", roomService.getAllRooms());
            model.addAttribute("users", userService.getAllUsers());

            return "reservation/index";
        }

        Reservation newReservation = new Reservation();
        newReservation.setId(reservationDto.getId());
        newReservation.setRoom(reservationDto.getRoom());
        newReservation.setUser(reservationDto.getUser());
        newReservation.setUseFromDatetime(reservationDto.getUseFromDatetime());
        newReservation.setUseToDatetime(reservationDto.getUseToDatetime());
        newReservation.setRemarks(reservationDto.getRemarks());

        reservationService.save(newReservation);

        // トースト表示用に成功メッセージをフラッシュスコープに保存
        redirectAttributes.addFlashAttribute("successMessage", "予約を登録しました");

        return "redirect:/reservation";
    }

    @GetMapping(value = "/reservation/{id}")
    public String updateReservation(@PathVariable Long id,
            Model model) {

        Reservation reservation = reservationService.findById(id);

        ReservationDto reservationDto = new ReservationDto();
        reservationDto.setId(reservation.getId());
        reservationDto.setRoom(reservation.getRoom());
        reservationDto.setUser(reservation.getUser());
        reservationDto.setUseFromDatetime(reservation.getUseFromDatetime());
        reservationDto.setUseToDatetime(reservation.getUseToDatetime());
        reservationDto.setRemarks(reservation.getRemarks());

        model.addAttribute("reservationDto", reservationDto);

        return "reservation/edit";
    }

    @PostMapping(value = "/reservation/{id}", params = "_method=delete")
    public String deleteReservation(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        reservationService.deleteById(id);
        redirectAttributes.addFlashAttribute("successMessage", "会議室予約を削除しました。");
        return "redirect:/reservation";
    }

    @GetMapping("reservation/calendar")
    public String calendarViewThisMonth(Model model) {
        LocalDate today = LocalDate.now();
        int year = today.getYear();
        int month = today.getMonthValue(); // 1〜12の数値
        return this.calendarView(year, month, model);
    }

    @GetMapping("reservation/calendar/{year}/{month}")
    public String calendarView(
            @PathVariable int year,
            @PathVariable int month,
            Model model) {

        YearMonth yearMonth;
        try {
            yearMonth = YearMonth.of(year, month);
        } catch (DateTimeException e) {
            // 不正な年月は今月にフォールバック
            yearMonth = YearMonth.now();
        }

        LocalDate firstDay = yearMonth.atDay(1);
        LocalDate lastDay = yearMonth.atEndOfMonth();

        // 対象年月を絞ってデータ取得
        List<Reservation> reservations = reservationService.getReservationsByYearMonth(year, month);

        Map<String, List<Reservation>> reservationMap = reservations.stream()
                .filter(r -> r.getUseFromDatetime() != null)
                .map(r -> Map.entry(r, r.getUseFromDatetime().toLocalDate()))
                .filter(e -> !e.getValue().isBefore(firstDay) && !e.getValue().isAfter(lastDay))
                .collect(Collectors.groupingBy(
                        e -> e.getValue().toString(), // LocalDate -> "yyyy-MM-dd" 文字列に
                        Collectors.mapping(Map.Entry::getKey, Collectors.toList())));

        model.addAttribute("yearMonth", yearMonth);
        model.addAttribute("firstDay", firstDay);
        model.addAttribute("reservationMap", reservationMap);

        return "reservation/calendar";
    }

}
