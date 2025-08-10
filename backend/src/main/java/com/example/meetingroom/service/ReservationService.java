package com.example.meetingroom.service;

import java.time.LocalDateTime;
import java.util.List;

import com.example.meetingroom.entity.Reservation;

public interface ReservationService {
    Reservation findById(Long id);

    List<Reservation> getAllReservations();

    void save(Reservation reservation);

    void deleteById(Long id);

    boolean existsByUserId(Long userId);

    boolean existsByRoomId(Long roomId);

    List<Reservation> getReservationsByYearMonth(int year, int month);

    boolean existsOverlapForInsert(Long roomId, LocalDateTime newFrom, LocalDateTime newTo);

    boolean existsOverlapForUpdate(Long roomId, Long reservationId, LocalDateTime newFrom, LocalDateTime newTo);
}
