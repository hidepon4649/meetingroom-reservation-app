package com.example.meetingroom.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.meetingroom.entity.Reservation;
import com.example.meetingroom.repository.ReservationRepository;

@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationServiceImpl(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Override
    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    @Override
    public void save(Reservation reservation) {
        reservationRepository.save(reservation);
        return;
    }

    @Override
    public void deleteById(Long id) {
        reservationRepository.deleteById(id);
        return;
    }

    @Override
    public Reservation findById(Long id) {
        return reservationRepository.findById(id).orElseThrow(() -> {
            return new RuntimeException("該当する会議室予約が存在しません:" + id);
        });
    }

    @Override
    public boolean existsByUserId(Long userId) {
        return reservationRepository.existsByUserId(userId);
    }

    @Override
    public boolean existsByRoomId(Long roomId) {
        return reservationRepository.existsByRoomId(roomId);
    }
}
