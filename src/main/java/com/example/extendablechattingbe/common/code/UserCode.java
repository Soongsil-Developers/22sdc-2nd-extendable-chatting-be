package com.example.extendablechattingbe.common.code;

import com.example.extendablechattingbe.common.response.ResponseCode;

public enum UserCode implements ResponseCode {

    USER_CREATED(1100, "유저가 생성되었습니다."),
    USER_NOT_FOUND(1101, "유저를 찾을 수 없습니다."),
    NOT_ENOUGH_INFORMATION(1102, "추가 정보가 입력되지 않았습니다."),
    DUPLICATED_USERNAME(1103, "중복된 아이디입니다."),
    DUPLICATED_NICKNAME(1104, "중복된 닉네임입니다."),
    USER_DELETED(1105, "회원탈퇴가 성공적으로 진행되었습니다.")
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
