package com.example.extendablechattingbe.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * enableSimpleBroker: 내장 브로커 사용, prefix 가 붙은 메시지를 발행시 브로커가 처리함
 * /queue(1:1), /topic(1:N) 경로로 메시지가 들어올 때 브로커가 메시지를 구독자에게 전달함
 * setApplicationDestinationPrefixes: 메시지 핸들러로 라우팅 되는 Prefix
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketBrokerConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/queue", "/topic");
        registry.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/javatechie").withSockJS();
    }
}

