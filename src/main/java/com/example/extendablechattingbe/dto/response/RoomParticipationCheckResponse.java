package com.example.extendablechattingbe.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RoomParticipationCheckResponse {
    private boolean participating;
}
