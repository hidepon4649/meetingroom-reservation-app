package com.example.meetingroom.dto;

import java.time.LocalDateTime;

import com.example.meetingroom.entity.Room;
import com.example.meetingroom.entity.User;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ReservationDto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    @ManyToOne
    @JoinColumn(name = "user_id")
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
