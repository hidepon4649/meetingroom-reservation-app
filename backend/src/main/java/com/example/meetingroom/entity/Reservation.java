package com.example.meetingroom.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime useFromDatetime;
    private LocalDateTime useToDatetime;
    private String remarks;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

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
