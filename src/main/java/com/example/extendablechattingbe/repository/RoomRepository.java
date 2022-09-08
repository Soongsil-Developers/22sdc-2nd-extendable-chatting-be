package com.example.extendablechattingbe.repository;

import com.example.extendablechattingbe.room.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long> {

    @Query("select r from Room r join fetch r.participants")
    Optional<Room> findRoomWithParticipantById(Long roomId);

}
