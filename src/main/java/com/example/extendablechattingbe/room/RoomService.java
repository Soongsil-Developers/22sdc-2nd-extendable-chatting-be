package com.example.extendablechattingbe.room;

import com.example.extendablechattingbe.chat.ChatController;
import com.example.extendablechattingbe.chat.code.ChatCode;
import com.example.extendablechattingbe.chat.dto.request.MessageLeaveRequest;
import com.example.extendablechattingbe.chat.dto.request.MessageParticipateRequest;
import com.example.extendablechattingbe.common.response.Response;
import com.example.extendablechattingbe.common.response.SocketResponse;
import com.example.extendablechattingbe.participant.ParticipantService;
import com.example.extendablechattingbe.participant.entity.Participant;
import com.example.extendablechattingbe.participant.exception.NotParticipateException;
import com.example.extendablechattingbe.room.code.RoomCode;
import com.example.extendablechattingbe.room.dto.RoomDto;
import com.example.extendablechattingbe.room.dto.response.MyRoomResponse;
import com.example.extendablechattingbe.room.dto.response.RoomCreateResponse;
import com.example.extendablechattingbe.room.dto.response.RoomInfoDto;
import com.example.extendablechattingbe.room.dto.response.RoomParticipationResponse;
import com.example.extendablechattingbe.room.entity.Room;
import com.example.extendablechattingbe.room.exception.RoomNotFoundException;
import com.example.extendablechattingbe.user.UserRepository;
import com.example.extendablechattingbe.user.dto.UserDto;
import com.example.extendablechattingbe.user.entity.User;
import com.example.extendablechattingbe.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class RoomService {

    private final RoomRepository roomRepository;
    private final ParticipantService participantService;
    private final UserRepository userRepository;
    private final ChatController chatController;

    // 방 생성
    public RoomCreateResponse saveRoom(UserDto userDto, RoomDto roomDto) {
        // 유저 찾기
        User user = findUserEntityById(userDto.getId());

        // 방 entity 생성
        Room room = Room.of(roomDto.getRoomName(), roomDto.getRoomContent(),
                roomDto.getLimitUserCount(), user);

        // 방 entity 저장
        roomRepository.save(room);

        // 방 만든 사람 -> 방 참여자에 추가
        participantService.saveParticipant(user, room);

        return RoomCreateResponse.builder()
                .createdRoomId(room.getId())
                .build();
    }

    // 방 전체 조회
    @Transactional(readOnly = true)
    public Page<RoomInfoDto> getRooms(Pageable pageable) {
        return roomRepository.findAll(pageable).map(RoomInfoDto::fromEntity);
    }

    // 하나의 방 조회
    @Transactional(readOnly = true)
    public RoomInfoDto getRoom(Long roomId) {
        // 방 찾기
        Room room = findRoomEntityById(roomId);

        return RoomInfoDto.fromEntity(room);
    }

    // 내가 참여한 방 조회
    @Transactional(readOnly = true)
    public MyRoomResponse getMyRoom(UserDto userDto) {
        User user = findUserEntityById(userDto.getId());

        Set<Participant> participants = user.getParticipants();

        List<Room> participatingRooms = new ArrayList<>();

        for (Participant participant : participants) {
            participatingRooms.add(participant.getRoom());
        }

        List<RoomDto> rooms = participatingRooms.stream().map(RoomDto::fromEntity).collect(Collectors.toList());
        return MyRoomResponse.builder()
                .rooms(rooms)
                .build();
    }

    // 방 수정
    public Response modifyRoom(UserDto userDto, RoomDto roomDto) {
        // 유저 찾기
        User user = findUserEntityById(userDto.getId());

        // 방 찾기
        Room room = findRoomEntityById(roomDto.getId());

        // 참가 여부
        boolean participate = participantService.checkAlreadyParticipate(user, room);

        if (participate) {
            throw new NotParticipateException("채팅방의 참가한 사람만 변경 가능합니다.");
        }

        room.updateRoom(roomDto.getRoomName(), roomDto.getRoomContent(), roomDto.getLimitUserCount());

        // Web Socket 방 수정 메시지 보내기
        // 수정된 정보를 DB 에서 가져오기 위해서 -> 1차 캐시에 적용
        Room updatedRoom = findRoomWithParticipantById(room.getId());
        SocketResponse<RoomParticipationResponse> response = SocketResponse.of(ChatCode.ROOM_UPDATED,
                RoomParticipationResponse.builder()
                        .roomDto(RoomDto.fromEntity(updatedRoom))
                        .build());

        sendMessage(room.getId(), response);

        return Response.of(RoomCode.ROOM_INFO_CHANGED, null);
    }

    // 방 참가하기
    public Response participateRoom(UserDto userDto, Long roomId) {
        // 유저 찾기
        User user = findUserEntityById(userDto.getId());

        //방 찾기
        Room room = findRoomWithParticipantById(roomId);

        // 인원이 가득찬 경우
        if(room.getParticipants().size() >= room.getLimitUserCount()){
            return Response.of(RoomCode.ROOM_IS_FULL, null);
        }

        // 참가 저장
        participantService.saveParticipant(user, room);

        // Web Socket 메세지 생성
        SocketResponse socketResponse = SocketResponse.of(ChatCode.USER_ENTER,
                MessageParticipateRequest.fromEntity(user));

        // Web Socket 참가 메세지 보내기
        sendMessage(room.getId(), socketResponse);

        return Response.of(RoomCode.SUCCESS_PARTICIPATE, null);
    }

    // 방 나가기
    public Response exitRoom(UserDto userDto, Long roomId) {

        User user = findUserEntityById(userDto.getId());

        Room room = findRoomWithParticipantById(roomId);

        if (!participantService.checkAlreadyParticipate(user, room)) {
            return Response.of(RoomCode.NOT_PARTICIPATE_ROOM, null);
        }

        // participant 관계 테이블 삭제
        participantService.exitRoom(user, room);

        // 채팅방을 삭제(방 인원이 0명인 경우)
        if(room.getParticipants().size() == 0){
            roomRepository.deleteById(room.getId());

            return Response.of(RoomCode.USER_EXIT, null);
        }

        MessageLeaveRequest messageLeaveRequest = MessageLeaveRequest.fromEntity(user);

        // Web Socket 메세지
        SocketResponse socketResponse = SocketResponse.of(ChatCode.USER_EXIT, messageLeaveRequest);

        // Web Socket Leave 메세지 보내기
        sendMessage(room.getId(), socketResponse);

        // Web Socket 방 수정 메시지 보내기
        // 수정된 정보를 DB 에서 가져오기 위해서 -> 1차 캐시에 적용
        Room updatedRoom = findRoomWithParticipantById(room.getId());
        SocketResponse<RoomParticipationResponse> response = SocketResponse.of(ChatCode.ROOM_UPDATED,
                RoomParticipationResponse.builder()
                        .roomDto(RoomDto.fromEntity(updatedRoom))
                        .build());

        sendMessage(room.getId(), response);

        return Response.of(RoomCode.USER_EXIT, null);
    }

    // 참여 여부 조회
    @Transactional(readOnly = true)
    public boolean checkParticipation(UserDto userDto, Long roomId) {
        // 유저 찾기
        User user = findUserEntityById(userDto.getId());
        // 방 찾기
        Room room = findRoomEntityById(roomId);
        // 참가 여부
        return participantService.checkAlreadyParticipate(user, room);
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


    private Room findRoomWithParticipantById(Long roomId) {
        return roomRepository.findRoomWithParticipantById(roomId)
                .orElseThrow(() -> new RoomNotFoundException("해당 채팅방을 찾을 수 없습니다."));
    }


    public void sendMessage(Long roomId, SocketResponse response) {
        chatController.sendServerMessage(roomId, response);
    }

}
