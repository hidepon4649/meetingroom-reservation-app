package com.example.meetingroom.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.meetingroom.entity.Room;
import com.example.meetingroom.repository.RoomRepository;

@Service
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;

    public RoomServiceImpl(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    @Override
    @Transactional
    public void save(Room room) {
        roomRepository.save(room);
        return;
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        roomRepository.deleteById(id);
        return;
    }

    @Override
    public Room findById(Long id) {
        return roomRepository.findById(id).orElseThrow(() -> {
            return new RuntimeException("該当会議室が存在しません：" + id);
        }

        );
    }

}
