package com.example.extendablechattingbe.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ChatMessage {
    private String sender;
    private String content;
    private MessageType type;

    enum  MessageType {
        CHAT, LEAVE, JOIN,
    }
}
