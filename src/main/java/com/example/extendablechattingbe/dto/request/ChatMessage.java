package com.example.extendablechattingbe.dto.request;

import com.example.extendablechattingbe.model.Chat;
import com.example.extendablechattingbe.model.ChatType;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ChatMessage {

    private String message;
    private ChatType type;
    private Long roomId;
    private String username;

    public static ChatMessage from(Chat chat) {
        return ChatMessage.builder()
                .message(chat.getMessage())
                .type(chat.getType())
                .roomId(chat.getRoom().getId())
                .username(chat.getUser().getUserName())
                .build();
    }

}
