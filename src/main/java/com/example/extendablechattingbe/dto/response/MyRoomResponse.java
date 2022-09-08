package com.example.extendablechattingbe.room.dto.response;

import com.example.extendablechattingbe.room.dto.RoomDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class MyRoomResponse {
    private List<RoomDto> rooms;
}
