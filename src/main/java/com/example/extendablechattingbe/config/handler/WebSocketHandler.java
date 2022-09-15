package com.example.extendablechattingbe.config.handler;

import com.example.extendablechattingbe.dto.request.ChatRequest;
import com.example.extendablechattingbe.model.ChatType;
import com.example.extendablechattingbe.repository.ParticipantRepository;
import com.example.extendablechattingbe.service.ChatService;
import com.example.extendablechattingbe.service.RoomService;
import com.example.extendablechattingbe.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

@RequiredArgsConstructor
@Slf4j
@Component
public class WebSocketHandler extends TextWebSocketHandler {
    private static final HashMap<Long, Set<WebSocketSession>> chatMap = new HashMap<>();
    private final ChatService chatService;
    private final UserService userService;

    private final RoomService roomService;
    private final ParticipantRepository participantRepository;
    private final ObjectMapper objectMapper;

    @Override
    @Transactional
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        String payload = message.getPayload();
        log.info("Client Payload : {}", payload);

        try {
            ChatRequest chatRequest = objectMapper.readValue(payload, ChatRequest.class);

            // db에 반영
            chatService.save(chatRequest);

            Long roomId = chatRequest.getRoomId();
            if (chatRequest.getType().equals(ChatType.TALK)) {
                for (WebSocketSession wss : chatMap.get(roomId)) {
                    wss.sendMessage(new TextMessage(payload));
                }
            }
        } catch (IOException e) {
            log.error("Error : {}", e.getMessage());
        }
    }

    @Override
    @Transactional
    public void afterConnectionEstablished(WebSocketSession session) {
        String json = jsonStrFrom(URLDecoder.decode(Objects.requireNonNull(session.getUri()).getQuery(), StandardCharsets.UTF_8));

        System.out.println(URLDecoder.decode(Objects.requireNonNull(session.getUri()).getQuery(), StandardCharsets.UTF_8));

        try {
            Map<String, String> map = objectMapper.readValue(json, HashMap.class);

            long roomId = Long.parseLong(map.get("roomId"));
            String username = map.get("username");

            // local session에 저장
            chatMap.computeIfAbsent(roomId, key -> new HashSet<>());
            chatMap.get(roomId).add(session);

            // db에 반영
            userService.participateRoom(username, roomId);
            chatService.save(new ChatRequest("[SYSTEM]:" + username + ChatType.ENTER.getMsg(), ChatType.ENTER, roomId, username));

            // log
            log.info("Connections Established : {}", session);

            for (WebSocketSession wss : chatMap.get(roomId)) {
                wss.sendMessage(new TextMessage(username + ChatType.ENTER.getMsg()));
            }

        } catch (IOException e) {
            log.error("Error : {}", e.getMessage());
        }
    }

    @Override
    @Transactional
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        String json = jsonStrFrom(URLDecoder.decode(Objects.requireNonNull(session.getUri()).getQuery(), StandardCharsets.UTF_8));

        try {
            Map<String, String> map = objectMapper.readValue(json, Map.class);
            Long roomId = Long.parseLong(map.get("roomId"));
            String username = map.get("username");

            // local session에서 제거
            chatMap.get(roomId).remove(session);

            // DB에 반영
            userService.leaveRoom(username, roomId);
            chatService.save(new ChatRequest("[SYSTEM]:" + username + ChatType.EXIT.getMsg(), ChatType.EXIT, roomId, username));

            log.info("[DISCONNECT]");

            for (WebSocketSession ws : chatMap.get(roomId)) {
                ws.sendMessage(new TextMessage(username + ChatType.EXIT.getMsg()));
            }

            if (participantRepository.countByRoom(roomId) == 0) {
                roomService.deleteRoom(roomId);
            }

        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private String jsonStrFrom(String str) {
        StringBuilder res = new StringBuilder("{\"");

        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == '=') {
                res.append("\"" + ":" + "\"");
            } else if (str.charAt(i) == '&') {
                res.append("\"" + "," + "\"");
            } else {
                res.append(str.charAt(i));
            }
        }
        res.append("\"" + "}");

        return res.toString();
    }
}
