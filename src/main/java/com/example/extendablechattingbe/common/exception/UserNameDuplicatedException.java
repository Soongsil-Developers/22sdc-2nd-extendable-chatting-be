package com.example.extendablechattingbe.common.exception;


public class UserNameDuplicatedException extends RuntimeException {

    public UserNameDuplicatedException(String message) {
        super(message);
    }

}
