package com.example.extendablechattingbe.dto.request;

import com.example.extendablechattingbe.user.entity.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MessageLeaveRequest {
    private Long id;
    private String userNickname;

    public static MessageLeaveRequest fromEntity(User user) {
        return MessageLeaveRequest.builder()
                .id(user.getId())
                .userNickname(user.getNickname())
                .build();
    }
}
