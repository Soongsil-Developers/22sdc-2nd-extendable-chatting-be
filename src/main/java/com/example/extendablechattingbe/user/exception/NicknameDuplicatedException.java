package com.example.extendablechattingbe.user.exception;

public class NicknameDuplicatedException extends RuntimeException {

    public NicknameDuplicatedException(String message) {
        super(message);
    }
}
