package com.example.meetingroom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.meetingroom.entity.Room;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

}
