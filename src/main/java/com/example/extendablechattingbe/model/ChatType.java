package com.example.extendablechattingbe.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public enum ChatType {

    ENTER("님이 채팅방에 입장하셨습니다."),
    TALK("한 유저의 채팅"),
    EXIT("님이 채팅방에 나가셨습니다.");

    @Getter
    private final String msg;

}
