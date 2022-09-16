package com.example.extendablechattingbe.kafka;

import com.example.extendablechattingbe.common.handler.WebSocketHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Slf4j
@Service
public class KafkaConsumer {

    private final WebSocketHandler webSocketHandler;

    @KafkaListener(topics = "chat-topic")
    public void processMessage(String kafkaMessage) {
        log.info("Kafka Message : {}", kafkaMessage);
        webSocketHandler.sendMsgFromTopic(kafkaMessage);
    }

}
