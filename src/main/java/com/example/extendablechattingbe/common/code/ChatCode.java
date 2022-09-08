package com.example.extendablechattingbe.common.code;

import com.example.extendablechattingbe.common.response.ResponseCode;

public enum ChatCode implements ResponseCode {

    USER_ENTER(1300, "유저 1명이 참여했습니다."),
    USER_EXIT(1301, "유저 1명이 나갔습니다."),
    USER_ONLINE(1302, "유저 1명이 온라인이 되었습니다."),
    USER_OFFLINE(1303, "유저 1명이 오프라인이 되었습니다."),
    CHAT_PUBLISH(1304, "유저 1명이 채팅을 보냈습니다."),
    ROOM_UPDATED(1305, "방 정보가 업데이트 되었습니다."),
    ;

    private final int code;
    private final String message;

    ChatCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

}
