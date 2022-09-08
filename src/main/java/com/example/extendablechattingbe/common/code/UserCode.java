package com.example.extendablechattingbe.user.code;

import com.example.extendablechattingbe.common.response.ResponseCode;

public enum UserCode implements ResponseCode {

    USER_NOT_FOUND(1100, "유저를 찾을 수 없습니다."),
    NOT_ENOUGH_INFORMATION(1101, "추가 정보가 입력되지 않았습니다."),
    DUPLICATED_USERNAME(1102, "중복된 아이디입니다."),
    DUPLICATED_NICKNAME(1103, "중복된 닉네임입니다."),
    ;
    private final int code;
    private final String message;

    UserCode(int code, String message) {
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
