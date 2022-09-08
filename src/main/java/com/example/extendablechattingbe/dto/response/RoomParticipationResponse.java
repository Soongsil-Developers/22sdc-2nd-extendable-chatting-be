package com.example.extendablechattingbe.room.dto.response;

import com.example.extendablechattingbe.room.dto.RoomDto;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class RoomParticipationResponse {

    private RoomDto roomDto;

}
