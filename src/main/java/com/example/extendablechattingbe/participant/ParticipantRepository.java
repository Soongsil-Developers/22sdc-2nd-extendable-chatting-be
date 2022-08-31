package com.example.extendablechattingbe.participant;

import com.example.extendablechattingbe.participant.entity.Participant;
import com.example.extendablechattingbe.room.entity.Room;
import com.example.extendablechattingbe.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipantRepository extends JpaRepository<Participant, Long> {
    boolean existsByRoomAndUser(Room room, User user);
}
