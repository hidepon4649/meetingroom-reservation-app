package com.example.meetingroom.service;

import java.util.List;

import com.example.meetingroom.entity.Reservation;

public interface ReservationService {
    Reservation findById(Long id);

    List<Reservation> getAllReservations();

    void save(Reservation reservation);

    void deleteById(Long id);

    boolean existsByUserId(Long userId);

    boolean existsByRoomId(Long roomId);
}
