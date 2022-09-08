package com.example.extendablechattingbe.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ChatPubRoomResponse {
    private Long userId;
    private String nickname;
    private String userProfileImagePath;
    private String message;
    private LocalDateTime sendAt;

}
