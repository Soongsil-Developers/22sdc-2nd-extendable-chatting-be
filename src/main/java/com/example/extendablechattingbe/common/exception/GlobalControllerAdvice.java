package com.example.extendablechattingbe.common.exception;

import com.example.extendablechattingbe.common.response.Response;
import com.example.extendablechattingbe.room.code.RoomCode;
import com.example.extendablechattingbe.room.exception.RoomNotFoundException;
import com.example.extendablechattingbe.user.code.UserCode;
import com.example.extendablechattingbe.user.exception.NicknameDuplicatedException;
import com.example.extendablechattingbe.user.exception.NotEnoughInformationException;
import com.example.extendablechattingbe.user.exception.UserNameDuplicatedException;
import com.example.extendablechattingbe.user.exception.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Response> handleUserNotFountException() {
        return ResponseEntity.badRequest().body(Response.of(UserCode.USER_NOT_FOUND, null));
    }

    @ExceptionHandler(UserNameDuplicatedException.class)
    public ResponseEntity<Response> handleUsernameDuplicationException() {
        return ResponseEntity.ok().body(Response.of(UserCode.DUPLICATED_USERNAME, null));
    }

    @ExceptionHandler(NicknameDuplicatedException.class)
    public ResponseEntity<Response> handleNicknameDuplicationException() {
        return ResponseEntity.ok().body(Response.of(UserCode.DUPLICATED_NICKNAME, null));
    }

    @ExceptionHandler(NotEnoughInformationException.class)
    public ResponseEntity<Response> handleNotEnoughInformationException() {
        return ResponseEntity.ok().body(Response.of(UserCode.NOT_ENOUGH_INFORMATION, null));
    }

    @ExceptionHandler(RoomNotFoundException.class)
    public ResponseEntity<Response> handleNotFoundRoomException(){
        return ResponseEntity.ok().body(Response.of(RoomCode.ROOM_NOT_FOUND, null));
    }

}
