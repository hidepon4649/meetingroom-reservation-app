package com.example.meetingroom.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.meetingroom.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Page<User> findAll(Pageable pageable);

    // ログイン用のメソッド
    Optional<User> findByEmailAndPassword(String email, String password);

    // メールアドレスからユーザを取得するメソッド
    Optional<User> findByEmail(String email);

}
