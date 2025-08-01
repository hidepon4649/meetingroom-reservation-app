package com.example.meetingroom.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.meetingroom.entity.Reservation;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    boolean existsByRoomId(Long roomId);

    boolean existsByUserId(Long userId);

    List<Reservation> findByUseFromDatetimeBetween(LocalDateTime start, LocalDateTime end);

}
