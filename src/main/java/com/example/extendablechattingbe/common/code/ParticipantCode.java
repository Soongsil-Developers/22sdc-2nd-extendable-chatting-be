package com.example.extendablechattingbe.common.code;

import com.example.extendablechattingbe.common.response.ResponseCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ParticipantCode implements ResponseCode {

    PARTICIPANT_CREATED(1300, "성공적으로 채팅방에 참여했습니다."),
    PARTICIPANT_DELETED(1301, "성공적으로 채팅방에서 나가셨습니다."),
    ;

    private final int code;
    private final String message;

    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

}
