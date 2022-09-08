package com.example.extendablechattingbe.session;

import com.example.extendablechattingbe.participant.entity.Participant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SocketSessionRepository extends JpaRepository<SocketSession, Long> {

    SocketSession findBySocketSessionId(String socketSessionId);

    void deleteBySocketSessionIdAndParticipant(String socketSessionId, Participant participant);

}
