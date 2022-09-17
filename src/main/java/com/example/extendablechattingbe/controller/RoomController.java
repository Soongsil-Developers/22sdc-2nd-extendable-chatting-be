package com.example.extendablechattingbe.controller;

import com.example.extendablechattingbe.common.code.CommonCode;
import com.example.extendablechattingbe.common.code.RoomCode;
import com.example.extendablechattingbe.common.response.Response;
import com.example.extendablechattingbe.dto.ChatDto;
import com.example.extendablechattingbe.dto.request.ChatMessage;
import com.example.extendablechattingbe.dto.RoomDto;
import com.example.extendablechattingbe.dto.request.RoomCreateRequest;
import com.example.extendablechattingbe.service.ChatService;
import com.example.extendablechattingbe.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RequestMapping("/rooms")
@RestController
public class RoomController {

    private final RoomService roomService;
    private final ChatService chatService;


    @PostMapping
    public ResponseEntity<Response<RoomDto>> createRoom(@RequestBody RoomCreateRequest createRequest) {
        return ResponseEntity.ok(Response.of(RoomCode.ROOM_CREATED, roomService.createRoom(createRequest)));
    }

    @GetMapping("{roomId}")
    public ResponseEntity<Response<RoomDto>> getRoom(@PathVariable Long roomId) {
        return ResponseEntity.ok(Response.of(CommonCode.GOOD_REQUEST, roomService.getRoom(roomId)));
    }

    @GetMapping
    public ResponseEntity<Response<Page<RoomDto>>> getRooms(
            @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(Response.of(CommonCode.GOOD_REQUEST, roomService.getRooms(pageable)));
    }

    @DeleteMapping("{roomId}")
    public ResponseEntity<Response<RoomDto>> deleteRoom(@PathVariable Long roomId) {
        RoomDto roomDto = roomService.deleteRoom(roomId);
        return ResponseEntity.ok(Response.of(RoomCode.ROOM_DELETED, roomDto));
    }


    /**
     * CHAT API
     */
    @GetMapping("{roomId}/chats/{chatId}")
    public ResponseEntity<Response<ChatMessage>> getChat(@PathVariable Long roomId, @PathVariable Long chatId) {
        ChatMessage chat = chatService.getChat(chatId);
        return ResponseEntity.ok(Response.of(CommonCode.GOOD_REQUEST, chat));
    }

    @GetMapping("{roomId}/chats")
    public ResponseEntity<Response<Page<ChatDto>>> getChats(@PathVariable Long roomId, @PageableDefault Pageable pageable) {
        Page<ChatDto> chats = chatService.getChats(roomId, pageable);
        return ResponseEntity.ok(Response.of(CommonCode.GOOD_REQUEST, chats));
    }

    @DeleteMapping("{roomId}/chats/{chatId}")
    public ResponseEntity<Response<ChatMessage>> deleteChat(@PathVariable("roomId") Long roomId, @PathVariable("chatId") Long chatId) {
        ChatMessage chat = chatService.deleteChat(chatId);
        return ResponseEntity.ok(Response.of(CommonCode.GOOD_REQUEST, chat));
    }

}
