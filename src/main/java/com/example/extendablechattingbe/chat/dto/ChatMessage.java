package com.example.extendablechattingbe.chat.dto;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ChatMessage {

    private MessageType type;
    private String roomId;
    private String sender;
    @Setter
    private String message;

}
