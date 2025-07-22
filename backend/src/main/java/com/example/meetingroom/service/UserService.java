package com.example.meetingroom.service;

import java.util.List;

import com.example.meetingroom.entity.User;

public interface UserService {

    List<User> getAllUsers();

    void save(User user);
}
