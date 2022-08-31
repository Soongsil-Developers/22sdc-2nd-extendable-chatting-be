package com.example.extendablechattingbe.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SocketResponse<T> {

    private ResponseCode responseCode;
    private T result;

    public static <T> SocketResponse<T> of(ResponseCode responseCode, T result) {
        return new SocketResponse<>(responseCode, result);
    }

}
