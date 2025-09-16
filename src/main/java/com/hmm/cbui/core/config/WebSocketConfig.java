/* (C) 2025 HMM Corp. All rights reserved. */
package com.hmm.cbui.core.config;

import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter;

import com.hmm.cbui.global.websocket.router.WebSocketRouter;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Configuration
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketHandler {

//  private final WebSocketRouter webSocketRouter; // 👈 주입 대상 변경

//  @Override
//  public void configureMessageBroker(MessageBrokerRegistry config) {
//    config
//        .enableSimpleBroker("/topic") // 메세지를 구독하는 요청 설정
//        .setHeartbeatValue(new long[] {10000, 10000}); // 10초마다 heartbeat 설정
//    //                .setTaskScheduler(msgBrokerTaskScheduler());
//    config.setApplicationDestinationPrefixes("/app"); // 메세지를 발행하는 요청 설정
//  }
//
//  @Override
//  public void registerStompEndpoints(StompEndpointRegistry registry) {
//    registry.addEndpoint("/ws").setAllowedOriginPatterns("*").withSockJS();
//  }

//  @Bean
//  public HandlerMapping webSocketHandlerMapping() {
//    // 이제 모든 WebSocket 요청은 WebSocketRouter가 담당합니다.
//    Map<String, Object> map =
//        Map.of(
//            "/ws/**", webSocketRouter // 👈 핸들러 변경
//            );
//    return new SimpleUrlHandlerMapping(map, -1);
//  }
//
//  @Bean
//  public WebSocketHandlerAdapter handlerAdapter() {
//    return new WebSocketHandlerAdapter();
//  }
    @Override
    public Mono<Void> handle(WebSocketSession session) {
        Flux<WebSocketMessage> output = session
                .receive()
                .map(WebSocketMessage::getPayloadAsText)
                .map(msg -> "Echo: " + msg)
                .map(session::textMessage);

        return session.send(output);
    }

}
