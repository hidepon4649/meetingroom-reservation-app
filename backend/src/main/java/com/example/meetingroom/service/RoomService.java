package com.example.meetingroom.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.example.meetingroom.entity.Room;

public interface RoomService {

    Room findById(Long id);

    List<Room> getAllRooms();

    void save(Room room);

    void deleteById(Long id);

    Page<Room> getRoomsPage(int page, int size);
}
