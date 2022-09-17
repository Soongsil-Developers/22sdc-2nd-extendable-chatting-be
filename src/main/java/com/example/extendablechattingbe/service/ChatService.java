package com.example.extendablechattingbe.service;

import com.example.extendablechattingbe.common.exception.ChatNotFoundException;
import com.example.extendablechattingbe.common.exception.RoomNotFoundException;
import com.example.extendablechattingbe.common.exception.UserNotFoundException;
import com.example.extendablechattingbe.dto.ChatDto;
import com.example.extendablechattingbe.dto.request.ChatMessage;
import com.example.extendablechattingbe.model.Chat;
import com.example.extendablechattingbe.model.Room;
import com.example.extendablechattingbe.model.User;
import com.example.extendablechattingbe.repository.ChatRepository;
import com.example.extendablechattingbe.repository.RoomRepository;
import com.example.extendablechattingbe.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class ChatService {

    private final ChatRepository chatRepository;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;


    public void save(ChatMessage request) {
        User user = getUserOrException(request.getUsername());

        Room room = getRoomOrException(request.getRoomId());

        Chat chat = Chat.of(request.getMessage(), request.getType(), user, room);
        chatRepository.save(chat);
    }

    @Transactional(readOnly = true)
    public ChatMessage getChat(Long chatId) {
        Chat chat = getChatOrException(chatId);
        return ChatMessage.from(chat);
    }

    @Transactional(readOnly = true)
    public Page<ChatDto> getChats(Long roomId, Pageable pageable) {
        Room room = getRoomOrException(roomId);
        return chatRepository.findByRoom(room, pageable).map(ChatDto::from);
    }

    public ChatMessage deleteChat(Long chatId) {
        Chat chat = getChatOrException(chatId);
        chatRepository.delete(chat);
        return ChatMessage.from(chat);
    }


    private User getUserOrException(String username) {
        return userRepository.findByUserName(username).orElseThrow(() -> new UserNotFoundException("해당 유저를 찾을 수 없습니다."));
    }

    private Room getRoomOrException(Long roomId) {
        return roomRepository.findById(roomId).orElseThrow(() -> new RoomNotFoundException("해당 채팅방을 찾을 수 없습니다."));
    }

    private Chat getChatOrException(Long chatId) {
        return chatRepository.findById(chatId).orElseThrow(() -> new ChatNotFoundException("해당 채팅을 찾을 수 없습니다."));
    }
}
