package com.example.meetingroom.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.meetingroom.entity.Reservation;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

        Page<Reservation> findAll(Pageable pageable);

        boolean existsByRoomId(Long roomId);

        boolean existsByUserId(Long userId);

        List<Reservation> findByUseFromDatetimeBetween(LocalDateTime start, LocalDateTime end, Sort sort);

        /**
         * 時間帯重複チェック(新規登録用)
         * 
         * @param roomId
         * @param newFrom
         * @param newTo
         * @return true: 重複あり, false: 重複なし
         */
        @Query("""
                                SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END
                                FROM Reservation r
                                WHERE r.room.id = :roomId
                                AND r.useFromDatetime < :newTo
                                AND r.useToDatetime > :newFrom
                        """)
        boolean existsOverlapForInsert(
                        @Param("roomId") Long roomId,
                        @Param("newFrom") LocalDateTime newFrom,
                        @Param("newTo") LocalDateTime newTo);

        /**
         * 時間帯重複チェック(更新用)。
         * 自身の予約ID(編集中の予約ID)を除外してチェック。
         * 
         * @param roomId
         * @param reservationId
         * @param newFrom
         * @param newTo
         * @return true: 重複あり, false: 重複なし
         */
        @Query("""
                                SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END
                                FROM Reservation r
                                WHERE r.room.id = :roomId
                                AND r.id <> :reservationId
                                AND r.useFromDatetime < :newTo
                                AND r.useToDatetime > :newFrom
                        """)
        boolean existsOverlapForUpdate(
                        @Param("roomId") Long roomId,
                        @Param("reservationId") Long reservationId,
                        @Param("newFrom") LocalDateTime newFrom,
                        @Param("newTo") LocalDateTime newTo);

}
