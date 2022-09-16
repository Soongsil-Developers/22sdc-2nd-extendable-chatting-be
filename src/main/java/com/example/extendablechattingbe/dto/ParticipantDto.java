package com.example.extendablechattingbe.dto;

import com.example.extendablechattingbe.model.Participant;
import com.example.extendablechattingbe.model.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ParticipantDto {

    private Long id;
    private String username;
    private Long roomId;

    public static ParticipantDto from(Participant participant) {
        return ParticipantDto.builder()
                .id(participant.getId())
                .username(participant.getUser().getUserName())
                .roomId(participant.getRoom().getId())
                .build();
    }

}
