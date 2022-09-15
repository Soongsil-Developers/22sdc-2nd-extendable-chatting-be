package com.example.extendablechattingbe.common.code;

import com.example.extendablechattingbe.common.response.ResponseCode;

public enum RoomCode implements ResponseCode {

    ROOM_CREATED(1200, "채팅방이 생성되었습니다."),
    ROOM_NOT_FOUND(1201, "해당하는 방이 존재하지 않습니다."),
    ROOM_DELETED(1202, "채팅방이 삭제되었습니다."),
    ROOM_INFO_CHANGED(1203, "방 정보가 업데이트 되었습니다."),
    SUCCESS_PARTICIPATE(1204, "방에 참가 하였습니다."),
    NOT_PARTICIPATE_ROOM(1205, "참가하지 않은 방입니다."),
    ABLE_TO_PARTICIPATE(1206, "해당 방에 참가할 수 있습니다."),
    PARTICIPATING_ROOM(1207, "이미 참여하고 있는 방입니다."),
    ;

    private final int code;
    private final String message;

    RoomCode(int code, String message) {
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
