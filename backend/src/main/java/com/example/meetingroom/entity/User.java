package com.example.meetingroom.entity;

import java.util.Base64;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "メールアドレスは必須です")
    @Email(message = "メールアドレスが不正です")
    private String email;

    private boolean isAdmin;

    @NotBlank(message = "名前は必須です")
    @Size(min = 2, max = 24, message = "名前は{min}文字以上、{max}文字以下です")
    private String name;

    private String password;

    @NotBlank(message = "telは必須です")
    @Size(min = 9, max = 20, message = "telは{min}文字以上、{max}文字以下です")
    private String tel;

    @NotBlank(message = "部署は必須です")
    @Size(min = 2, max = 255, message = "部署は{min}文字以上、{max}文字以下です")
    private String department;

    @Lob
    private byte[] picture;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPicture() {
        if (picture != null) {
            return Base64.getEncoder().encodeToString(picture);
        }
        return null;

    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", email=" + email + ", isAdmin=" + isAdmin + ", name=" + name + ", password="
                + password + ", tel=" + tel + ", department=" + department + "]";
    }

}
