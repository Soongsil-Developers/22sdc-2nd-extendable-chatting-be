package com.example.extendablechattingbe.participant;

import com.example.extendablechattingbe.room.entity.Room;
import com.example.extendablechattingbe.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class ParticipantService {

    private final ParticipantRepository participantRepository;


    public void saveParticipant(Room room, User user) {

    }
}
