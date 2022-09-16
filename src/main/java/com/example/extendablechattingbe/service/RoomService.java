package com.example.extendablechattingbe.service;

import com.example.extendablechattingbe.common.exception.RoomNotFoundException;
import com.example.extendablechattingbe.dto.RoomDto;
import com.example.extendablechattingbe.dto.request.RoomCreateRequest;
import com.example.extendablechattingbe.model.Room;
import com.example.extendablechattingbe.repository.ParticipantRepository;
import com.example.extendablechattingbe.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Service
public class RoomService {

    private final RoomRepository roomRepository;


    @Transactional
    public RoomDto createRoom(RoomCreateRequest request) {
        Room room = Room.of(request.getName(), request.getContent(), request.getLimitUserCount());

        roomRepository.save(room);

        return RoomDto.from(room);
    }

    public Page<RoomDto> getRooms(Pageable pageable) {
        return roomRepository.findAll(pageable).map(RoomDto::from);
    }

    public RoomDto getRoom(Long roomId) {
        Room room = getRoomOrException(roomId);
        return RoomDto.from(room);
    }

    public RoomDto deleteRoom(Long roomId) {
        Room room = getRoomOrException(roomId);

        roomRepository.delete(room);
        return RoomDto.from(room);
    }


    private Room getRoomOrException(Long roomId) {
        return roomRepository.findById(roomId)
                .orElseThrow(() -> new RoomNotFoundException("해당 채팅방을 찾을 수 없습니다."));
    }

}
