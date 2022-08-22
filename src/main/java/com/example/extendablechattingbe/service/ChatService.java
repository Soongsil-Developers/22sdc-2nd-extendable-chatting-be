package com.example.extendablechattingbe.service;

import com.example.extendablechattingbe.domain.Chat;
import com.example.extendablechattingbe.dto.ChatMessage;
import com.example.extendablechattingbe.repository.ChatRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class ChatService {

    private final ChatRepository chatRepository;

    public void save(ChatMessage chatMessage) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String message = mapper.writeValueAsString(chatMessage);
            Chat chat = Chat.builder()
                    .room(null)
                    .message(message)
                    .build();
            chatRepository.save(chat);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
