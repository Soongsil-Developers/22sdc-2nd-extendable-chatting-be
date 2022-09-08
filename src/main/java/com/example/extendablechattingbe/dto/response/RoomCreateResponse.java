package com.example.extendablechattingbe.room.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RoomCreateResponse {
    private Long createdRoomId;
}
