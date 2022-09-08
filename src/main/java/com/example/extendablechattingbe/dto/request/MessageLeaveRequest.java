package com.example.extendablechattingbe.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MessageLeaveRequest {
    private Long id;
    private String userNickname;

}
