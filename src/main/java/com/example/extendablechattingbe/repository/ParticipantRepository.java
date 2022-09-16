package com.example.extendablechattingbe.repository;

import com.example.extendablechattingbe.model.Participant;
import com.example.extendablechattingbe.model.Room;
import com.example.extendablechattingbe.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ParticipantRepository extends JpaRepository<Participant, Long> {

    Optional<Participant> findByRoomAndUser(Room room, User user);

    @Query("select p from Participant p join fetch p.room where p.user = :user")
    List<Participant> findByUser(@Param("user") User user);

    @Query("select count(p) from Participant p where p.room.id = :roomId")
    int countByRoom(@Param("roomId") Long roomId);

}
