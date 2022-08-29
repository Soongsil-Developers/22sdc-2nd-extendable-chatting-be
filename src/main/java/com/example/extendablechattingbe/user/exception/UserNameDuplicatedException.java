package com.example.extendablechattingbe.user.exception;

public class UserNameDuplicatedException extends RuntimeException {

    public UserNameDuplicatedException(String message) {
        super(message);
    }
}
