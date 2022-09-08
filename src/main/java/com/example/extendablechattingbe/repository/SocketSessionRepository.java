package com.example.extendablechattingbe.repository;


import com.example.extendablechattingbe.model.SocketSession;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SocketSessionRepository extends JpaRepository<SocketSession, Long> {

    SocketSession findBySocketSessionId(String socketSessionId);

//    void deleteBySocketSessionIdAndParticipant(String socketSessionId, Participant participant);

}
