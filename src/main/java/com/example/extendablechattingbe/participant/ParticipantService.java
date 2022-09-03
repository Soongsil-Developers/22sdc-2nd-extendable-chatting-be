package com.example.extendablechattingbe.participant;

import com.example.extendablechattingbe.participant.entity.Participant;
import com.example.extendablechattingbe.participant.exception.NotParticipateException;
import com.example.extendablechattingbe.room.RoomRepository;
import com.example.extendablechattingbe.room.entity.Room;
import com.example.extendablechattingbe.room.exception.RoomNotFoundException;
import com.example.extendablechattingbe.session.SocketSessionService;
import com.example.extendablechattingbe.user.UserRepository;
import com.example.extendablechattingbe.user.entity.User;
import com.example.extendablechattingbe.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class ParticipantService {

    private final RoomRepository roomRepository;
    private final ParticipantRepository participantRepository;
    private final UserRepository userRepository;
    private final SocketSessionService socketSessionService;

    public void saveParticipant(User user, Room room) {
        // 방에 기존에 참여한 유저이면, return
        if(participantRepository.existsByUserAndRoom(user, room)){
            return;
        }

        // 참여하지 않았을 때 participant 저장
        Participant participant = Participant.of(room, user);
        participantRepository.save(participant);
    }

    @Transactional(readOnly = true)
    public boolean checkAlreadyParticipate(User user, Room room){
        return participantRepository.existsByUserAndRoom(user, room);
    }

    @Transactional(readOnly = true)
    public boolean checkAlreadyParticipate(Long userId, Long roomId){
        User user = findUserEntityById(userId);
        Room room = findRoomEntityById(roomId);

        return participantRepository.existsByUserAndRoom(user, room);
    }

    public void saveSession(String sessionId, Participant participant){
        socketSessionService.saveSessionId(sessionId, participant);
    }

    public void exitRoom(User user, Room room){
        Participant participant = participantRepository.findByUserAndRoom(user, room)
                .orElseThrow(() -> new NotParticipateException("해당 방에 참여하지 않은 유저입니다."));

        participantRepository.delete(participant);
    }

    @Transactional(readOnly = true)
    public Participant verifySession(String sessionId, Long userId, Long roomId){
        User user = findUserEntityById(userId);
        Room room = findRoomEntityById(roomId);

        return participantRepository.findByUserAndRoom(user, room)
                .orElseThrow(() -> new NotParticipateException("해당 방에 참여하지 않은 유저입니다."));
    }

    @Transactional(readOnly = true)
    public Participant findParticipantBySessionId(String sessionId){
        return socketSessionService.findParticipantBySessionId(sessionId);
    }

    public void deleteSession(String sessionId, Participant participant){
        participant.sessionRemove(sessionId);
        socketSessionService.deleteSession(sessionId, participant);
        participant.checkOnOffline();
    }

    // 유저 찾기
    private User findUserEntityById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("해당 유저를 찾을 수 없습니다."));
    }

    // 채팅방 찾기
    private Room findRoomEntityById(Long roomId) {
        return roomRepository.findById(roomId)
                .orElseThrow(() -> new RoomNotFoundException("해당 채팅방을 찾을 수 없습니다."));
    }

}
