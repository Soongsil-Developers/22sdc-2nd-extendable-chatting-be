package com.example.extendablechattingbe.service;

import com.example.extendablechattingbe.repository.SocketSessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class SocketSessionService {

    private final SocketSessionRepository socketSessionRepository;

//    @Transactional(readOnly = true)
//    public Participant findParticipantBySessionId(String sessionId) {
//        SocketSession socketSession = socketSessionRepository.findBySocketSessionId(sessionId);
//        return socketSession.getParticipant();
//    }
//
//    public void saveSessionId(String sessionId, Participant participant) {
//        SocketSession socketSession = socketSessionRepository.save(SocketSession.of(sessionId, participant));
//        participant.getSocketSessions().add(socketSession);
//    }
//
//    public void deleteSession(String sessionId, Participant participant){
//        socketSessionRepository.deleteBySocketSessionIdAndParticipant(sessionId, participant);
//    }
}

