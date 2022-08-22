package com.example.extendablechattingbe.controller;

import com.example.extendablechattingbe.dto.ChatMessage;
import com.example.extendablechattingbe.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @MessageMapping("/chat.register")
    @SendTo("/topic/public")
    public ChatMessage greeting(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) throws Exception {
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        return chatMessage;
    }

    @MessageMapping("/chat.send")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
        chatService.save(chatMessage);
        return chatMessage;
    }
}
