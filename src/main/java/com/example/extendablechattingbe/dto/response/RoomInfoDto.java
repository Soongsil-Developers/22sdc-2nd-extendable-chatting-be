package com.example.extendablechattingbe.room.dto.response;

import com.example.extendablechattingbe.room.entity.Room;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RoomInfoDto {
    private String name;
    private String content;

    public static RoomInfoDto fromEntity(Room room) {
        return RoomInfoDto.builder()
                .name(room.getRoomName())
                .content(room.getRoomContent())
                .build();
    }

}
