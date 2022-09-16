package com.example.extendablechattingbe.dto;

import com.example.extendablechattingbe.model.Room;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class RoomDto {

    private Long id;
    private String roomName;
    private String roomContent;
    private LocalDateTime createdTime;

    public static RoomDto from(Room room) {
        return RoomDto.builder()
                .id(room.getId())
                .roomName(room.getRoomName())
                .roomContent(room.getRoomContent())
                .createdTime(room.getCreatedTime())
                .build();
    }

}
