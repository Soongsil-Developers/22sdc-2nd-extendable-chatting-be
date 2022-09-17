package com.example.extendablechattingbe.dto;

import com.example.extendablechattingbe.model.Chat;
import com.example.extendablechattingbe.model.ChatType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ChatDto {
    private Long id;
    private String message;
    private ChatType type;
    private Long roomId;
    private String username;
    private LocalDateTime sendAt;

    public static ChatDto from(Chat chat) {
        return ChatDto.builder()
                .id(chat.getId())
                .message(chat.getMessage())
                .type(chat.getType())
                .roomId(chat.getRoom().getId())
                .username(chat.getUser().getUserName())
                .sendAt(chat.getSendAt())
                .build();
    }

}

