package com.example.extendablechattingbe.common.exception;

import com.example.extendablechattingbe.common.code.CommonCode;
import com.example.extendablechattingbe.common.response.Response;
import com.example.extendablechattingbe.room.code.RoomCode;
import com.example.extendablechattingbe.user.code.UserCode;
import lombok.extern.slf4j.Slf4j;
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
    public ResponseEntity<Response> handleNotFoundRoomException() {
        return ResponseEntity.ok().body(Response.of(RoomCode.ROOM_NOT_FOUND, null));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Response> handlerRuntimeException(RuntimeException e) {
        log.error(e.getMessage());
        return ResponseEntity.ok(Response.of(CommonCode.INTERNAL_SERVER_ERROR, null));
    }

}
