package com.example.meetingroom.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.meetingroom.entity.User;
import com.example.meetingroom.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAllUsers() {

        return userRepository.findAll();

    }

    @Override
    public void save(User user) {
        userRepository.save(user);
        return;
    }

}
