package com.example.extendablechattingbe.room.dto.request;

import com.example.extendablechattingbe.room.dto.RoomDto;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class RoomUpdateRequest {
    private String name;
    private String content;


    public RoomDto toDto(Long roomId) {
        return RoomDto.builder()
                .id(roomId)
                .roomName(name)
                .roomContent(content)
                .build();
    }

}
