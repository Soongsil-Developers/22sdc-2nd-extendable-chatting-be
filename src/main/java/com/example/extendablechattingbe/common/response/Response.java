package com.example.extendablechattingbe.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Response<T> {
    private final ResponseCode responseCode;
    private final T result;

    public static <T> Response<T> of(ResponseCode responseCode, T result) {
        return new Response<>(responseCode, result);
    }

}
