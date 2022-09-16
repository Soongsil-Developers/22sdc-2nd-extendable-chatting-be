package com.example.extendablechattingbe.controller;

import com.example.extendablechattingbe.common.code.CommonCode;
import com.example.extendablechattingbe.common.code.ParticipantCode;
import com.example.extendablechattingbe.common.code.UserCode;
import com.example.extendablechattingbe.common.response.Response;
import com.example.extendablechattingbe.dto.ParticipantDto;
import com.example.extendablechattingbe.dto.RoomDto;
import com.example.extendablechattingbe.dto.UserDto;
import com.example.extendablechattingbe.dto.request.UserJoinRequest;
import com.example.extendablechattingbe.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/users")
@RestController
public class UserController {

    private final UserService userService;


    @PostMapping
    public ResponseEntity<Response<UserDto>> join(@RequestBody UserJoinRequest request) {
        UserDto userDto = userService.join(request.getUsername());
        return ResponseEntity.ok(Response.of(UserCode.USER_CREATED, userDto));
    }

    @GetMapping("/{username}")
    public ResponseEntity<Response<UserDto>> getUser(@PathVariable String username) {
        UserDto userDto = userService.getUser(username);
        return ResponseEntity.ok(Response.of(CommonCode.GOOD_REQUEST, userDto));
    }

    @GetMapping("/{username}/rooms")
    public ResponseEntity<Response<List<RoomDto>>> getRooms(@PathVariable String username) {
        List<RoomDto> rooms = userService.getRooms(username);
        return ResponseEntity.ok().body(Response.of(CommonCode.GOOD_REQUEST, rooms));
    }

    @DeleteMapping("{username}")
    public ResponseEntity<Response<UserDto>> deleteUser(@PathVariable String username) {
        UserDto userDto = userService.deleteUser(username);
        return ResponseEntity.ok(Response.of(UserCode.USER_DELETED, userDto));
    }

    @PostMapping("/{username}/rooms/{roomId}")
    public ResponseEntity<Response<ParticipantDto>> participateRoom(@PathVariable String username, @PathVariable Long roomId) {
        ParticipantDto participantDto = userService.participateRoom(username, roomId);
        return ResponseEntity.ok(Response.of(ParticipantCode.PARTICIPANT_CREATED, participantDto));
    }


    @DeleteMapping("/{username}/rooms/{roomId}")
    public ResponseEntity<Response<ParticipantDto>> leaveRoom(@PathVariable String username, @PathVariable Long roomId) {
        ParticipantDto participantDto = userService.leaveRoom(username, roomId);
        return ResponseEntity.ok().body(Response.of(ParticipantCode.PARTICIPANT_DELETED, participantDto));
    }

}
