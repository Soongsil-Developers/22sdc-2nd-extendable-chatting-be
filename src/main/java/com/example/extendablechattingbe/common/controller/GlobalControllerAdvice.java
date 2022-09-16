package com.example.extendablechattingbe.common.controller;

import com.example.extendablechattingbe.common.code.CommonCode;
import com.example.extendablechattingbe.common.code.RoomCode;
import com.example.extendablechattingbe.common.code.UserCode;
import com.example.extendablechattingbe.common.exception.RoomNotFoundException;
import com.example.extendablechattingbe.common.exception.UserNameDuplicatedException;
import com.example.extendablechattingbe.common.exception.UserNotFoundException;
import com.example.extendablechattingbe.common.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalControllerAdvice {

    // userName
    @ExceptionHandler(UserNameDuplicatedException.class)
    public ResponseEntity<Response> handleUsernameDuplicationException() {
        return ResponseEntity.ok().body(Response.of(UserCode.DUPLICATED_USERNAME, null));
    }

    @ExceptionHandler(RoomNotFoundException.class)
    public ResponseEntity<Response<Void>> handleNotFoundRoomException() {
        return ResponseEntity.ok().body(Response.of(RoomCode.ROOM_NOT_FOUND, null));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Response<Void>> handleNotFoundUserException() {
        return ResponseEntity.ok().body(Response.of(UserCode.USER_NOT_FOUND, null));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Response<Void>> handlerRuntimeException(RuntimeException e) {
        log.error(e.getMessage());
        return ResponseEntity.ok(Response.of(CommonCode.INTERNAL_SERVER_ERROR, null));
    }

}
