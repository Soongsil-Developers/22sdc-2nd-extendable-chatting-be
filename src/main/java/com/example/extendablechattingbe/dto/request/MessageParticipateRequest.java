package com.example.extendablechattingbe.dto.request;

import com.example.extendablechattingbe.user.entity.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MessageParticipateRequest {
    private Long id;
    private String userNickname;

    public static MessageParticipateRequest fromEntity(User user) {
        return MessageParticipateRequest.builder()
                .id(user.getId())
                .userNickname(user.getNickname())
                .build();
    }

}
