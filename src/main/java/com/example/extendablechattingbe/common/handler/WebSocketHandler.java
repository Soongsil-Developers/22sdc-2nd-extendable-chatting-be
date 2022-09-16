package com.example.extendablechattingbe.common.handler;

import com.example.extendablechattingbe.dto.ParticipantDto;
import com.example.extendablechattingbe.dto.request.ChatMessage;
import com.example.extendablechattingbe.kafka.KafkaProducer;
import com.example.extendablechattingbe.model.ChatType;
import com.example.extendablechattingbe.service.ChatService;
import com.example.extendablechattingbe.service.RoomService;
import com.example.extendablechattingbe.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@RequiredArgsConstructor
@Slf4j
@Component
public class WebSocketHandler extends TextWebSocketHandler {

    private static final HashMap<Long, Set<WebSocketSession>> chatMap = new HashMap<>();
    private final ChatService chatService;
    private final UserService userService;
    private final RoomService roomService;
    private final ObjectMapper mapper;
    private final KafkaProducer kafkaProducer;

    @Override
    @Transactional
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        String payload = message.getPayload();
        log.info("Client Payload : {}", payload);

        try {
            ChatMessage msg = mapper.readValue(payload, ChatMessage.class);

            // db msg 저장
            chatService.save(msg);

            if (msg.getType().equals(ChatType.EXIT)) {
                Long roomId = msg.getRoomId();
                String username = msg.getUsername();

                // participant db에서 삭제
                ParticipantDto participantDto = userService.leaveRoom(username, roomId);

                // 방 인원 수 체크
                int count = userService.countByRoom(participantDto.getRoomId());

                // 방 인원수가 0명이면 방 삭제
                if (count == 0) {
                    roomService.deleteRoom(roomId);
                }

                // local session에서 삭제
                chatMap.get(msg.getRoomId()).remove(session);
                session.close();
            }

            // kafka producer
            kafkaProducer.sendMessage(msg);
        } catch (IOException e) {
            log.error("Error : {}", e.getMessage());
        }
    }

    @Override
    @Transactional
    public void afterConnectionEstablished(WebSocketSession session) {
        String jsonInStr = jsonStrFrom(URLDecoder.decode(Objects.requireNonNull(session.getUri()).getQuery(), StandardCharsets.UTF_8));

        try {
            ChatMessage msg = mapper.readValue(jsonInStr, ChatMessage.class);
            String username = msg.getUsername();
            Long roomId = msg.getRoomId();

            // local session에 저장
            chatMap.computeIfAbsent(roomId, key -> new HashSet<>());
            chatMap.get(roomId).add(session);

            // db에 반영
            userService.participateRoom(username, roomId);
            chatService.save(msg);

            // kafka producer
            kafkaProducer.sendMessage(msg);
        } catch (IOException e) {
            log.error("Error : {}", e.getMessage());
        }
    }

//    @Override
//    @Transactional
//    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
//        String jsonInStr = jsonStrFrom(URLDecoder.decode(Objects.requireNonNull(session.getUri()).getQuery(), StandardCharsets.UTF_8));
//
//        try {
//            ChatMessage request = mapper.readValue(jsonInStr, ChatMessage.class);
//            String username = request.getUsername();
//            Long roomId = request.getRoomId();
//
//            // local session에서 제거
//            chatMap.get(roomId).remove(session);
//
//            // DB에 반영
////            userService.leaveRoom(username, roomId);
////            chatService.save(new ChatRequest("[SYSTEM]:" + username + ChatType.EXIT.getMsg(), ChatType.EXIT, roomId, username));
//
//            log.info("[DISCONNECT]");
//
////            for (WebSocketSession ws : chatMap.get(roomId)) {
////                ws.sendMessage(new TextMessage(username + ChatType.EXIT.getMsg()));
////            }
//            kafkaProducer.sendMessage(request);
//
////            if (participantRepository.countByRoom(roomId) == 0) {
////                roomService.deleteRoom(roomId);
////            }
//
//        } catch (IOException e) {
//            log.error(e.getMessage());
//        }
//    }

    public void sendMsgFromTopic(String kafkaMessage) {
        // kafka consumer
        try {
            ChatMessage request = mapper.readValue(kafkaMessage, ChatMessage.class);
            Set<WebSocketSession> sessions = chatMap.get(request.getRoomId());

            if (sessions == null) {
                return;
            }

            for (WebSocketSession session : sessions) {
                try {
                    session.sendMessage(new TextMessage(kafkaMessage));
                } catch (IOException e) {
                    log.error(e.getMessage());
                }
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private String jsonStrFrom(String queryParameter) {
        StringBuilder res = new StringBuilder("{\"");

        for (int i = 0; i < queryParameter.length(); i++) {
            if (queryParameter.charAt(i) == '=') {
                res.append("\"" + ":" + "\"");
            } else if (queryParameter.charAt(i) == '&') {
                res.append("\"" + "," + "\"");
            } else {
                res.append(queryParameter.charAt(i));
            }
        }
        res.append("\"" + "}");

        return res.toString();
    }

}
