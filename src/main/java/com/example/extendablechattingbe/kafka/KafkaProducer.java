package com.example.extendablechattingbe.kafka;

import com.example.extendablechattingbe.dto.request.ChatMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class KafkaProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper mapper;

    public void sendMessage(ChatMessage message) {
        String jsonInString = "";
        try {
            jsonInString = mapper.writeValueAsString(message);
        } catch (JsonProcessingException e) {
            log.error("Error : {}", e.getMessage());
        }

        kafkaTemplate.send("chat-topic", jsonInString);
        log.info("Kafka producer send message : {}", jsonInString);
    }

}
