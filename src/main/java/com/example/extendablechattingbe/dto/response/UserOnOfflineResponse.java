package com.example.extendablechattingbe.dto.response;

import com.example.extendablechattingbe.chat.dto.MessageType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserOnOfflineResponse {
    private Long userId;
    private String userNickname;
    private MessageType messageType;
    private String message;
}
