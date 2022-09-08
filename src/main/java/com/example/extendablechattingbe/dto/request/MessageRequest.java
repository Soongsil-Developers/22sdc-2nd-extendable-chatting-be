package com.example.extendablechattingbe.dto.request;

import com.example.extendablechattingbe.chat.dto.MessageType;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MessageRequest {

    private MessageType messageType;
    private Long roomId;
    private String target;
    private String message;
    private Long userId;
    private String nickname;
}
