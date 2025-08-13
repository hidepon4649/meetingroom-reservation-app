package com.example.meetingroom.controller;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
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
import com.example.meetingroom.entity.User;
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
    public String index(Model model,
            @ModelAttribute("loginUserId") Long loginUserId,
            @ModelAttribute("isAdmin") Boolean isAdmin) {

        ReservationDto reservationDto = new ReservationDto();

        // 一般ユーザは自分の予約のみ表示。自分のidで予約ユーザを固定
        if (Boolean.FALSE.equals(isAdmin)) {
            User userEntity = new User();
            userEntity.setId(loginUserId);
            reservationDto.setUser(userEntity);
        }

        model.addAttribute("reservations", reservationService.getAllReservations());
        model.addAttribute("reservationDto", reservationDto);
        model.addAttribute("rooms", roomService.getAllRooms());
        model.addAttribute("users", userService.getAllUsers());

        return "reservation/index";
    }

    @GetMapping("reservation/date/{yyyyMMdd}")
    public String createReservationByDate(@PathVariable String yyyyMMdd,
            Model model,
            @ModelAttribute("loginUserId") Long loginUserId,
            @ModelAttribute("isAdmin") Boolean isAdmin) {

        ReservationDto reservationDto = new ReservationDto();

        // 一般ユーザは自分の予約のみ表示。自分のidで予約ユーザを固定
        if (Boolean.FALSE.equals(isAdmin)) {
            User userEntity = new User();
            userEntity.setId(loginUserId);
            reservationDto.setUser(userEntity);
        }

        // 選択された日付を初期設定
        LocalDateTime dateTime = LocalDateTime.parse(
                yyyyMMdd + "000000",
                DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));

        reservationDto.setUseFromDatetime(dateTime);
        reservationDto.setUseToDatetime(dateTime);

        model.addAttribute("reservations", reservationService.getAllReservations());
        model.addAttribute("reservationDto", reservationDto);
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

        // 管理者かどうか確認
        final Boolean isAdmin = (Boolean) model.getAttribute("isAdmin");
        final Long loginUserId = (Long) model.getAttribute("loginUserId");

        // 一般ユーザは他人の予約を作成できないよう制限
        if (!isAdmin && !loginUserId.equals(reservationDto.getUser().getId())) {
            bindingResult.rejectValue("user.id", "invalid.user", "他のユーザの予約は作成できません");
        }

        // 時間帯の重複チェック
        if (reservationService.existsOverlapForInsert(
                reservationDto.getRoom().getId(),
                reservationDto.getUseFromDatetime(),
                reservationDto.getUseToDatetime())) {
            bindingResult.rejectValue("useFromDatetime", "overlap.time", "指定された時間帯は既に予約されています");
        }

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

    @PostMapping(value = "/reservation/{id}", params = "_method=update")
    public String updateReservation(@PathVariable Long id,
            @Validated @ModelAttribute ReservationDto reservationDto,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            Model model) {

        // 管理者かどうか確認
        final Boolean isAdmin = (Boolean) model.getAttribute("isAdmin");
        final Long loginUserId = (Long) model.getAttribute("loginUserId");

        // 一般ユーザは他人の予約を作成できないよう制限
        if (!isAdmin && !loginUserId.equals(reservationDto.getUser().getId())) {
            bindingResult.rejectValue("user.id", "invalid.user", "他のユーザの予約は作成できません");
        }

        // 時間帯の重複チェック
        if (reservationService.existsOverlapForUpdate(
                reservationDto.getRoom().getId(),
                reservationDto.getId(),
                reservationDto.getUseFromDatetime(),
                reservationDto.getUseToDatetime())) {
            bindingResult.rejectValue("useFromDatetime", "overlap.time", "指定された時間帯は既に予約されています");
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("rooms", roomService.getAllRooms());
            model.addAttribute("users", userService.getAllUsers());
            return "reservation/edit";
        }

        Reservation reservation = reservationService.findById(id);
        reservation.setRoom(reservationDto.getRoom());
        reservation.setUser(reservationDto.getUser());
        reservation.setUseFromDatetime(reservationDto.getUseFromDatetime());
        reservation.setUseToDatetime(reservationDto.getUseToDatetime());
        reservation.setRemarks(reservationDto.getRemarks());

        reservationService.save(reservation);

        // トースト表示用に成功メッセージをフラッシュスコープに保存
        redirectAttributes.addFlashAttribute("successMessage", "会議室予約を編集しました。");

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
        model.addAttribute("rooms", roomService.getAllRooms());
        model.addAttribute("users", userService.getAllUsers());

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
