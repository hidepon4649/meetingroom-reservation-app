package com.example.meetingroom.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "部屋名称は必須です")
    @Size(min = 2, max = 255, message = "部屋名称は{min}文字以上、{max}文字以下です")
    private String name;

    @NotBlank(message = "場所は必須です")
    @Size(min = 2, max = 255, message = "場所は{min}文字以上、{max}文字以下です")
    private String place;

    @NotBlank(message = "telは必須です")
    @Size(min = 9, max = 20, message = "telは{min}文字以上、{max}文字以下です")
    private String tel;

    @NotBlank(message = "備考は必須です")
    @Size(min = 2, max = 255, message = "備考は{min}文字以上、{max}文字以下です")
    private String remarks;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Override
    public String toString() {
        return "Room [id=" + id + ", name=" + name + ", place=" + place + ", tel=" + tel + ", remarks=" + remarks + "]";
    }

}
