package com.example.extendablechattingbe.room.dto.request;

import com.example.extendablechattingbe.room.dto.RoomDto;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class RoomCreateRequest {
    private String name;
    private String content;
    private int limitUserCount;

    public RoomDto toDto() {
        return RoomDto.builder()
                .roomName(name)
                .roomContent(content)
                .limitUserCount(limitUserCount)
                .build();
    }

}
