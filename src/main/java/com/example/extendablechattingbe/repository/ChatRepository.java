package com.example.extendablechattingbe.repository;

import com.example.extendablechattingbe.model.Chat;
import com.example.extendablechattingbe.model.Room;
import com.example.extendablechattingbe.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface ChatRepository extends JpaRepository<Chat, Long> {

    @Query("select min(c.sendAt) from Chat c where c.room = :room and c.user = :user")
    Optional<LocalDateTime> findEnterTime(@Param("room") Room room, @Param("user") User user);

    @Query(value = "select c from Chat c join fetch c.user where c.room = :room",
            countQuery = "select count(c) from Chat c where c.room = :room")
    Page<Chat> findByRoom(@Param("room") Room room, Pageable pageable);

}
