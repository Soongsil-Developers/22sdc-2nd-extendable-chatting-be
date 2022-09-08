package com.example.extendablechattingbe.dto.response;

import com.example.extendablechattingbe.model.Chat;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ChatHistoryResponse {

    private Long userId;
    private String nickname;
    private String userProfileImagePath;
    private String message;
    private LocalDateTime sendAt;

    public static ChatHistoryResponse fromEntity(Chat chat) {
        return ChatHistoryResponse.builder()
                .userId(chat.getUser().getId())
                .nickname(chat.getUser().getNickname())
                .userProfileImagePath(chat.getUser().getUserProfileImagePath())
                .message(chat.getMessage())
                .sendAt(chat.getSendAt())
                .build();
    }

}
