package com.example.meetingroom.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.example.meetingroom.entity.User;

public interface UserService {

    User findById(Long id);

    List<User> getAllUsers();

    void save(User user);

    void deleteById(Long id);

    Page<User> getUsersPage(int page, int size);
}
