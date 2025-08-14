package com.example.meetingroom.dto;

import java.time.LocalDateTime;

import com.example.meetingroom.entity.Room;
import com.example.meetingroom.entity.User;
import com.example.meetingroom.validation.DateRange;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@DateRange(startField = "useFromDatetime", endField = "useToDatetime", message = "利用開始日時は利用終了日時より前でなければなりません")
public class ReservationDto {

    private Long id;

    @NotNull(message = "予約会議室は必須です")
    private Room room;
    @NotNull(message = "予約ユーザは必須です")
    private User user;

    @NotNull(message = "利用開始日時は必須です")
    private LocalDateTime useFromDatetime;

    @NotNull(message = "利用終了日時は必須です")
    private LocalDateTime useToDatetime;

    @NotBlank(message = "備考は必須です")
    @Size(min = 2, max = 255, message = "備考は{min}文字以上、{max}文字以下です")
    private String remarks;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getUseFromDatetime() {
        return useFromDatetime;
    }

    public void setUseFromDatetime(LocalDateTime useFromDatetime) {
        this.useFromDatetime = useFromDatetime;
    }

    public LocalDateTime getUseToDatetime() {
        return useToDatetime;
    }

    public void setUseToDatetime(LocalDateTime useToDatetime) {
        this.useToDatetime = useToDatetime;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Override
    public String toString() {
        return "Reservation [id=" + id + ", room=" + room + ", user=" + user + ", useFromDatetime=" + useFromDatetime
                + ", useToDatetime=" + useToDatetime + ", remarks=" + remarks + "]";
    }

}
