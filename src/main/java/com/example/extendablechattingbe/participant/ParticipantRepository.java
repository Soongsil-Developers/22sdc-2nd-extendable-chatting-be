package com.example.extendablechattingbe.participant;

import com.example.extendablechattingbe.participant.entity.Participant;
import com.example.extendablechattingbe.room.entity.Room;
import com.example.extendablechattingbe.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ParticipantRepository extends JpaRepository<Participant, Long> {
    boolean existsByUserAndRoom(User user, Room room);

    Optional<Participant> findByUserAndRoom(User user, Room room);

}
