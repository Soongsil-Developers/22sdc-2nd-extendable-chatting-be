package com.example.extendablechattingbe.service;

import com.example.extendablechattingbe.common.exception.NotParticipateException;
import com.example.extendablechattingbe.common.exception.RoomNotFoundException;
import com.example.extendablechattingbe.common.exception.UserNameDuplicatedException;
import com.example.extendablechattingbe.common.exception.UserNotFoundException;
import com.example.extendablechattingbe.dto.ParticipantDto;
import com.example.extendablechattingbe.dto.RoomDto;
import com.example.extendablechattingbe.dto.UserDto;
import com.example.extendablechattingbe.model.Participant;
import com.example.extendablechattingbe.model.Room;
import com.example.extendablechattingbe.model.User;
import com.example.extendablechattingbe.repository.ParticipantRepository;
import com.example.extendablechattingbe.repository.RoomRepository;
import com.example.extendablechattingbe.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class UserService {

    private final UserRepository userRepository;
    private final ParticipantRepository participantRepository;
    private final RoomRepository roomRepository;


    public UserDto join(String username) {
        User user = new User(username);

        Optional<User> findUser = userRepository.findByUserName(username);

        if (findUser.isPresent()) {
            throw new UserNameDuplicatedException("중복된 아이디입니다.");
        }

        userRepository.save(user);

        return UserDto.from(user);
    }

    @Transactional(readOnly = true)
    public UserDto getUser(String username) {
        User user = getUserOrException(username);
        return UserDto.from(user);
    }

    public UserDto deleteUser(String username) {
        User user = getUserOrException(username);
        userRepository.delete(user);
        return UserDto.from(user);
    }

    public ParticipantDto participateRoom(String username, Long roomId) {
        User user = getUserOrException(username);
        Room room = getRoomOrException(roomId);

        Participant participant = Participant.of(user, room);
        participantRepository.save(participant);

        return ParticipantDto.from(participant);
    }

    public ParticipantDto leaveRoom(String username, Long roomId) {
        User user = getUserOrException(username);
        Room room = getRoomOrException(roomId);

        Participant participant = participantRepository.findByRoomAndUser(room, user).orElseThrow(() -> new NotParticipateException("채팅방에 먼저 참여해주세요."));
        participantRepository.delete(participant);
        return ParticipantDto.from(participant);
    }

    public List<RoomDto> getRooms(String username) {
        User user = getUserOrException(username);
        List<RoomDto> rooms = participantRepository.findByUser(user).stream()
                .map(Participant::getRoom)
                .map(RoomDto::from)
                .collect(Collectors.toList());
        return rooms;
    }

    public int countByRoom(Long roomId) {
        return participantRepository.countByRoom(roomId);
    }


    private User getUserOrException(String username) {
        return userRepository.findByUserName(username).orElseThrow(() -> new UserNotFoundException("해당 유저를 찾을 수 없습니다."));
    }

    private Room getRoomOrException(Long roomId) {
        return roomRepository.findById(roomId).orElseThrow(() -> new RoomNotFoundException("해당 채팅방을 찾을 수 없습니다."));
    }

}
